package com.hotel;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

@SpringBootApplication
public class HotelApplication {

	public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		SpringApplication.run(HotelApplication.class, args);
		
	}

}
