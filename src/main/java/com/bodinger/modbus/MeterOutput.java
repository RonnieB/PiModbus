package com.bodinger.modbus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeterOutput {

    float Phase_1_line_to_neutral_volts = 0;
    float Phase_2_line_to_neutral_volts = 0;
    float Phase_3_line_to_neutral_volts = 0;
    float Phase_1_current = 0;
    float Phase_2_current = 0;
    float Phase_3_current = 0;
    float Phase_1_power = 0;
    float Phase_2_power = 0;
    float Phase_3_power = 0;
    float Average_line_toneutral_volts = 0;
    float Average_line_current = 0;
    float Sum_of_line_currents = 0;
    float Total_system_power = 0;
    float Import_wh_since_last_reset = 0;
    float Export_wh_since_last_reset = 0;


    private ObjectMapper mapper = new ObjectMapper();

    public String toString(){
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }


    public float getPhase_1_line_to_neutral_volts() {
        return Phase_1_line_to_neutral_volts;
    }

    public void setPhase_1_line_to_neutral_volts(float phase_1_line_to_neutral_volts) {
        Phase_1_line_to_neutral_volts = phase_1_line_to_neutral_volts;
    }

    public float getPhase_2_line_to_neutral_volts() {
        return Phase_2_line_to_neutral_volts;
    }

    public void setPhase_2_line_to_neutral_volts(float phase_2_line_to_neutral_volts) {
        Phase_2_line_to_neutral_volts = phase_2_line_to_neutral_volts;
    }

    public float getPhase_3_line_to_neutral_volts() {
        return Phase_3_line_to_neutral_volts;
    }

    public void setPhase_3_line_to_neutral_volts(float phase_3_line_to_neutral_volts) {
        Phase_3_line_to_neutral_volts = phase_3_line_to_neutral_volts;
    }

    public float getPhase_1_current() {
        return Phase_1_current;
    }

    public void setPhase_1_current(float phase_1_current) {
        Phase_1_current = phase_1_current;
    }

    public float getPhase_2_current() {
        return Phase_2_current;
    }

    public void setPhase_2_current(float phase_2_current) {
        Phase_2_current = phase_2_current;
    }

    public float getPhase_3_current() {
        return Phase_3_current;
    }

    public void setPhase_3_current(float phase_3_current) {
        Phase_3_current = phase_3_current;
    }

    public float getPhase_1_power() {
        return Phase_1_power;
    }

    public void setPhase_1_power(float phase_1_power) {
        Phase_1_power = phase_1_power;
    }

    public float getPhase_2_power() {
        return Phase_2_power;
    }

    public void setPhase_2_power(float phase_2_power) {
        Phase_2_power = phase_2_power;
    }

    public float getPhase_3_power() {
        return Phase_3_power;
    }

    public void setPhase_3_power(float phase_3_power) {
        Phase_3_power = phase_3_power;
    }

    public float getAverage_line_toneutral_volts() {
        return Average_line_toneutral_volts;
    }

    public void setAverage_line_toneutral_volts(float average_line_toneutral_volts) {
        Average_line_toneutral_volts = average_line_toneutral_volts;
    }

    public float getAverage_line_current() {
        return Average_line_current;
    }

    public void setAverage_line_current(float average_line_current) {
        Average_line_current = average_line_current;
    }

    public float getSum_of_line_currents() {
        return Sum_of_line_currents;
    }

    public void setSum_of_line_currents(float sum_of_line_currents) {
        Sum_of_line_currents = sum_of_line_currents;
    }

    public float getTotal_system_power() {
        return Total_system_power;
    }

    public void setTotal_system_power(float total_system_power) {
        Total_system_power = total_system_power;
    }

    public float getImport_wh_since_last_reset() {
        return Import_wh_since_last_reset;
    }

    public void setImport_wh_since_last_reset(float import_wh_since_last_reset) {
        Import_wh_since_last_reset = import_wh_since_last_reset;
    }

    public float getExport_wh_since_last_reset() {
        return Export_wh_since_last_reset;
    }

    public void setExport_wh_since_last_reset(float export_wh_since_last_reset) {
        Export_wh_since_last_reset = export_wh_since_last_reset;
    }
}
