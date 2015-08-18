package com.mongodb;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String hashedAndSalted = "hEXGArbm";
        String salt = hashedAndSalted.split(",")[0];
        System.out.println(salt);


    }
}
