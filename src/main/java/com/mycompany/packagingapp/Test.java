/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.packagingapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class Test {

    public static void main(String[] args) {
        Test t = new Test();
        t.evaluate();
    }

    public void evaluate() {
        StringBuilder sb = new StringBuilder();

        List<String> lineList = new ArrayList();
        lineList = getLinesAsList();
        String totWeightArray[][] = new String[lineList.size()][2];
        totWeightArray = getItemsLessThanTotalWeight(lineList);
        List<Double[][]> list = new ArrayList();
        list = comparePriceOfItems(totWeightArray);
        List<Double> totWeightList = new ArrayList();
        for (int a = 0; a < totWeightArray.length; a++) {
            totWeightList.add(Double.valueOf(totWeightArray[a][0]));
        }
        int boxCount = 0;
        for (int x = 0; x < list.size(); x++) {
            boxCount = x + 1;
            sb.append("Box No : ");
            Double totWeight = totWeightList.get(x);
            sb.append(boxCount + " Total Allowed Weight : " + totWeight);
            Double weightPriceArray[][] = list.get(x);
            java.util.Arrays.sort(weightPriceArray, new java.util.Comparator() {
                @Override
                public int compare(Object a, Object b) {
                    Double[] row1 = (Double[]) a;
                    Double[] row2 = (Double[]) b;

                    return row1[0].compareTo(row2[0]);
                }
            });
            Double netWeight = 0.0;
            for (int a = 0; a < weightPriceArray.length; a++) {

                if (weightPriceArray[a][0] != null) {
                    netWeight = netWeight + weightPriceArray[a][0];
                    if (netWeight <= totWeight) {
                        int itemNumber = a + 1;
                        sb.append(" Item number : " + itemNumber + " Item Weight : " + weightPriceArray[a][0] + " Price : $" + weightPriceArray[a][1]);

                    }
                } else {

                }

            }
            sb.append("\n");
        }
        System.out.println("sb : " + sb.toString());

    }

    private List<Double[][]> comparePriceOfItems(String items[][]) {
        List<Double[][]> list = new ArrayList();
        try {
            for (int x = 0; x < items.length; x++) {

                try {
                    String values = items[x][1].replaceAll("\\(", "");
                    values = values.replaceAll("\\)", "");
                    String line[] = values.split("~");
                    Double weightPriceArray[][] = new Double[line.length][2];
                    for (int a = 0; a < line.length; a++) {
                        String separatedValues[] = line[a].split(",");
                        try {
                            Double itemWeight = Double.valueOf(separatedValues[1]);
                            weightPriceArray[a][0] = itemWeight;
                            Double price = Double.valueOf(separatedValues[2].substring(1));
                            weightPriceArray[a][1] = price;
                        } catch (Exception e) {
                        }

                    }
                    list.add(weightPriceArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

//Step 2 : Filter Items whose weight is less than total weight
    private String[][] getItemsLessThanTotalWeight(List<String> lineList) {
        String filteredItems[][] = new String[lineList.size()][2];
        String newArray[][] = new String[lineList.size()][2];
        for (int x = 0; x < lineList.size(); x++) {
            // System.out.println("linelidt "  +lineList.get(x));
            String[] splitString = (lineList.get(x).split("\\s+"));
            newArray[x][0] = splitString[1];
            StringBuilder sb = new StringBuilder();
            for (int a = 2; a < splitString.length; a++) {
                sb.append(splitString[a] + "~");
            }
            sb = sb.deleteCharAt(sb.lastIndexOf("~"));
            newArray[x][1] = sb.toString();
        }

        for (int z = 0; z < newArray.length; z++) {
            // System.out.println("wt and value = " + newArray[z][0] + "   " + newArray[z][1]);
            String separatedValues[] = newArray[z][1].split("~");
            Double totalWeight = Double.valueOf(newArray[z][0]);
            StringBuilder ss = new StringBuilder();

            for (int a = 0; a < separatedValues.length; a++) {
                String weightArray[] = separatedValues[a].split(",");
                Double itemWeight = Double.valueOf(weightArray[1]);
                filteredItems[z][0] = totalWeight + "";
                if (itemWeight < totalWeight) {
                    //weightPriceArray = 
                    ss.append(separatedValues[a] + "~");
                    //weightPriceArray[0]
                }
            }

            try {
                ss = ss.deleteCharAt(ss.lastIndexOf("~"));
            } catch (Exception e) {
            }

            filteredItems[z][1] = ss.toString();

        }
        return filteredItems;
    }

    private List<String> getLinesAsList() {
        BufferedReader br = null;
        List<String> lineList = new ArrayList();
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader("D:\\dd.txt"));

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.trim().length() > 0) {
                    lineList.add(sCurrentLine.trim());
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return lineList;
    }

}
