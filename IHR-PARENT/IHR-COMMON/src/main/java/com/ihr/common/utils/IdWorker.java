package com.ihr.common.utils;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author: yangchun
 * @description:
 * @date: Created in 2020-06-13 17:47
 */
// 雪花算法实现
public class IdWorker {
    // 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定后就不变动）
    private final static long twepoch = 1288834974657L;
    // 机器标识位数
    private final static long workerIdBits = 5L;
    // 数据中心标识位数
    private final static long  datacenterIdBits = 5L;
    // 机器ID最大值
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 数据中心最大值
    private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    // 毫秒内自增位
    private final static long sequenceBits = 12L;
    // 机器id左偏移12位
    private final static long workerIdShift = sequenceBits +workerIdBits;
    // 数据中心左偏移17位
    private final static long datacenterIdShift = sequenceBits +workerIdBits;
    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIdBits+ datacenterIdBits;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

    /* 上次生产id时间戳 */

    private static long lastTimestamp = -1L;

    // 0,并发控制
    private long sequence = 0L;

    private final long workerId;

    private final long datacenterId;

    public IdWorker(){
        this.datacenterId = getDatacenterId(maxDatacenterId);
        this.workerId = getMaxWorkerId(datacenterId,maxWorkerId);
    }
    public IdWorker(long workerId,long datacenterId){
        if(workerId>maxWorkerId||workerId<0){
            throw new IllegalArgumentException(String.format("workId can'n be greater than %d or less than 0",maxWorkerId));
        }
        if(datacenterId>maxDatacenterId||datacenterId<0){
            throw new IllegalArgumentException(String.format("datacenterId can'n be greater than %d or less than 0",datacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
    public synchronized long nextId(){
        long timeStamp = timeGen();
        if(timeStamp<lastTimestamp){
            throw new RuntimeException(String.format("clock moved backwards. Refusing to generate id for %d milliseconds",lastTimestamp-timeStamp));
        }
        if(lastTimestamp==timeStamp){
            sequence = (sequence+1)&sequenceMask;
            if(sequence==0){
                timeStamp = tilNextMillis(lastTimestamp);
            }
        }else {
            sequence = 0L;
        }
        lastTimestamp = timeStamp;
        long nextId = ((timeStamp-twepoch)<<timestampLeftShift)|(datacenterId<<datacenterIdShift)|(workerId<<workerIdShift)|sequence
                ;
        return nextId;
    }
    private long timeGen(){
        return System.currentTimeMillis();
    }
    private long tilNextMillis(final long lastTimestamp){
        long timeStamp = this.timeGen();
        while (timeStamp<lastTimestamp){
            timeStamp = this.timeGen();
        }
        return timeStamp;
    }
    private long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if(!name.isEmpty()){
            mpid.append(name.split("@")[0]);
        }
        return (mpid.toString().hashCode()&0xffff)%(maxWorkerId+1);
    }

    private long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try{
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip);
            if(networkInterface==null){
                id =1L;
            }else {
                byte[] mac = networkInterface.getHardwareAddress();
                // 取mac地址低32位右移6位
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDatacenterId + 1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }
}
