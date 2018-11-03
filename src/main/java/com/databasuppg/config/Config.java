package com.databasuppg.config;


import lombok.Getter;

import java.io.*;

public class Config {

    private static final String PATH = System.getProperty("user.dir") + "/config.cfg";
    @Getter private String APIKey;
    @Getter private int width, height;


    public Config() { loadConfig(); }

    private void loadConfig() {
        File file = new File(PATH);
        String result = "";

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            //TODO a better implement of this this.
            while ((line = br.readLine()) != null) {
                if(line.contains("api_key"))        {this.APIKey = extract(line);}
                else if(line.contains("width"))     {this.width = Integer.parseInt(extract(line));}
                else if(line.contains("height"))    {this.height = Integer.parseInt(extract(line));}
            }

        } catch (IOException e) { e.getMessage(); }
    }

    // Extract the value between the quotes.
    private String extract(String line) {
        line = line.substring(line.indexOf("\"") +1);
        line = line.substring(0, line.indexOf("\""));

        return line;
    }


}
