/**
 * Wine tasting Problem
 * Author: khushbu Kumari
 */
package com.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * @author khushbuk
 *
 */
public class DoTaskHashMap {
	static final String inputFilename = "person_wine_4.txt"; /* input file path */
	static final String outputFilename = "result4.txt"; /* output file path */
	/*
	 * input wine data
	 */
	static private HashSet<Long> wineList = new HashSet<Long>();
	/*
	 * input wine data
	 */
	static HashMap<Long, ArrayList<Long>> output;

	/*
	 * Main Controller Method
	 * 
	 **/
	public static void main(String[] args) {

		HashMap<Long, ArrayList<Long>> input;
		input = readInput();
		for (Long val : input.keySet()) {
			System.out.print("Wine" + val + "  wanted by :  ");
			for (Long val1 : input.get(val)) {
				System.out.print("Person" + val1 + "  ");
			}
			System.out.println("");
		}

		doProcessing(input);
		writeData();

	}

	/*
	 * method to prepare output data
	 * 
	 **/
	private static void doProcessing(HashMap<Long, ArrayList<Long>> input) {
		ArrayList<Long> list;
		output = new HashMap<Long, ArrayList<Long>>();
		int size = wineList.size();
		for (Long val : wineList) {
			int counter = 0;
			while (counter < 10) {
				int index = (int) ((Math.random()) * size);
				list = input.get(val);
				if (index < list.size()) {
					Long person = list.get(index);
					if (!output.containsKey(person)) {
						output.put(person, new ArrayList<Long>());
					}
					if (output.get(person).size() < 3) {
						output.get(person).add(val);
						break;
					}
					counter++;
				}
		}
		}
	}

	/*
	 * reading input from the input file
	 * 
	 **/
	public static HashMap<Long, ArrayList<Long>> readInput() {
		HashMap<Long, ArrayList<Long>> input = new HashMap<Long, ArrayList<Long>>();
		String line;
		ArrayList<Long> tempList;

		try (BufferedReader br = new BufferedReader(new FileReader(inputFilename))) {

			while ((line = br.readLine()) != null) {
				StringTokenizer token = new StringTokenizer(line);
				String personID = token.nextToken().replaceFirst("person", "");
				String wineID = token.nextToken().replaceFirst("wine", "");
				Long person = Long.parseLong(personID);
				Long wine = Long.parseLong(wineID);
				wineList.add(wine);
				if (input.containsKey(wine)) {
					tempList = input.get(wine);
					tempList.add(person);
					
					input.put(wine, tempList);
				} else {
					tempList = new ArrayList<>();
					tempList.add(person);
					input.put(wine, tempList);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;

	}

	/*
	 * writing data in input file
	 * 
	 **/
	public static void writeData() {
		int count =0;

		try (Writer writer = new BufferedWriter(new FileWriter(new File(outputFilename)))) {
			writer.write("                                                       \n");
			for (Long val : output.keySet()) {
				for (Long val1 : output.get(val)) {
					count++;
					writer.write("Person" + val + "\t" + "Wine" + val1 + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Write the count to the first line

				try (RandomAccessFile resultFile = new RandomAccessFile(new File(outputFilename), "rws")) {
					resultFile.seek(0);
					resultFile.write(String.valueOf("Total number of wine bottles sold by Apan : "+count + "\n").getBytes());

				} catch (IOException e) {
					e.printStackTrace();
				}

	}

}
