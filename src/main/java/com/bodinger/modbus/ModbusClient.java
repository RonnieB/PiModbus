package com.bodinger.modbus;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.data.ModbusHoldingRegisters;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.msg.request.ReadHoldingRegistersRequest;
import com.intelligt.modbus.jlibmodbus.msg.request.ReadInputRegistersRequest;
import com.intelligt.modbus.jlibmodbus.msg.response.ReadHoldingRegistersResponse;
import com.intelligt.modbus.jlibmodbus.msg.response.ReadInputRegistersResponse;
import com.intelligt.modbus.jlibmodbus.serial.*;
import jssc.SerialPortList;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class ModbusClient {

    public static void main(String[] args) {
        new ModbusClient().run();
    }

    SerialParameters sp;
    ModbusMaster modbusMaster;

    private void run(){
        try {
            init();
            getRegister(0);
            MeterOutput meterOutput = getMeterOutput();
            System.out.println(meterOutput);
            BufferedWriter writer = new BufferedWriter(new FileWriter("out.json"));
            writer.write(meterOutput.toString());
            writer.close();

        } catch (SerialPortException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() throws SerialPortException, ModbusIOException {
        //    public static void main(String[] args) {
        sp = new SerialParameters();
        Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);

        sp.setDevice("/dev/serial0");
        sp.setBaudRate(SerialPort.BaudRate.BAUD_RATE_9600);
        sp.setDataBits(8);
        sp.setParity(SerialPort.Parity.NONE);
        sp.setStopBits(1);
        SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
        modbusMaster = ModbusMasterFactory.createModbusMasterRTU(sp);
        modbusMaster.connect();

    }


    public MeterOutput getMeterOutput(){
        MeterOutput m = new MeterOutput();
        try {
            m.setPhase_1_line_to_neutral_volts(getRegister(0));
            m.setPhase_2_line_to_neutral_volts(getRegister(2));
            m.setPhase_3_line_to_neutral_volts(getRegister(4));
            m.setPhase_1_current(getRegister(6));
            m.setPhase_2_current(getRegister(8));
            m.setPhase_3_current(getRegister(10));
            m.setPhase_1_power(getRegister(12));
            m.setPhase_2_power(getRegister(14));
            m.setPhase_3_power(getRegister(16));
            m.setAverage_line_toneutral_volts(getRegister(42));
            m.setAverage_line_current(getRegister(46));
            m.setSum_of_line_currents(getRegister(48));
            m.setTotal_system_power(getRegister(52));
            m.setImport_wh_since_last_reset(getRegister(72));
            m.setExport_wh_since_last_reset(getRegister(74));
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        } catch (ModbusProtocolException e) {
            e.printStackTrace();
        }



    float Phase_1_current = 0;
    float Phase_2_current = 0;
    float Phase_3_current = 0;
    float Average_line_toneutral_volts = 0;
    float Average_line_current = 0;
    float Sum_of_line_currents = 0;
    float Total_system_power = 0;
    float Import_wh_since_last_reset = 0;
    float Export_wh_since_last_reset = 0;
        return m;
    }


    public float getRegister(int register) throws ModbusNumberException, ModbusIOException, ModbusProtocolException {
        int slaveId = 1;
        int offset = register;
        int quantity = 2;

        ReadInputRegistersRequest request = new ReadInputRegistersRequest();
        request.setServerAddress(slaveId);
        request.setStartAddress(offset);
        request.setQuantity(quantity);

        ReadInputRegistersResponse response = (ReadInputRegistersResponse) request.getResponse();

        modbusMaster.processRequest(request);
        ModbusHoldingRegisters registers = response.getHoldingRegisters();

        System.out.println("Register:" + register);

        if(registers.getQuantity() == 2) {
            int i1 = registers.get(0);
            int i2 = registers.get(1);
            return floatFromInt(i1,i2);
        }
        return 0f;
    }

    static public float floatFromInt(int i1,int i2){
        byte lowByteI1 = (byte)(i1 & 0xFF);
        byte penultimateByteI1 = (byte)((i1 >> 8) & 0xFF);
        byte lowByteI2 = (byte)(i2 & 0xFF);
        byte penultimateByteI2 = (byte)((i2 >> 8) & 0xFF);
        // System.out.println("Lb1:" + lowByteI1 + " hb1:" + penultimateByteI1 + " lb2:" + lowByteI2 + " hb2:" + penultimateByteI2);
        byte[] bytes = {penultimateByteI1,lowByteI1,penultimateByteI2,lowByteI2};
        float fb = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
        //System.out.println("Int1:" + i1 + " Int2:" + i2 + " Float Big:" + fb);
        return fb;
    }

}
