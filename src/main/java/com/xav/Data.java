package com.xav;

import java.io.*;
public class Data {

    // Variables defined for each object
    double distanceInKm; //stores the distance between each set of restaurant & customer / restaurant & restaurant / customer &customer
    int timeInMinutes; //stores time to travel between each set of restaurant & customer / restaurant & restaurant / customer &customer
    boolean acceptVisit; //whether a visit exists between each set of restaurant & customer / restaurant & restaurant / customer &customer

    static int request[] = new int[4]; //Working with 4 customers
    static int shelfTime[] = new int[4]; //shelf time of foods ordered, to be assigned by restaurants

    //default constructor
    public Data()
    {
        distanceInKm = 0.0;
        timeInMinutes = 0;
        acceptVisit = false;
    }
    //parametrized constructor
    public Data(double distance, int time, boolean accept)
    {
        distanceInKm = distance;
        timeInMinutes = time;
        acceptVisit = accept;
    }

    //initialization function
    public static void initialization()
    {
        //Object array for Restaurant & Customer
        Data RestaurantCustomer[][] = new Data[3][4];
        Data RestaurantRestaurant[][] = new Data[3][3];
        Data CustomerCustomer[][] = new Data[4][4];
        int i, j;
        // Restaurant & Customer Array
        System.out.println("Restaurant(Rows) & Customer(Columns) Array");
        for(i=0; i<3; i++) {
            for (j = 0; j < 4; j++) {
                RestaurantCustomer[i][j] = new Data(10, 40, false);
                RestaurantCustomer[i][j].display();
            }
            System.out.println();
        }
        System.out.println();

        // Restaurant & Restaurant Array
        System.out.println("Restaurant(Rows) & Restaurant(Columns) Array");
        for(i=0; i<3; i++) {
            for (j = 0; j < 3; j++) {
                RestaurantRestaurant[i][j] = new Data(5, 15, false);
                RestaurantRestaurant[i][j].display();
            }
            System.out.println();
        }
        System.out.println();

        // Customer & Customer Array
        System.out.println("Customer(Rows) & Customer(Columns) Array");
        for(i=0; i<3; i++) {
            for (j = 0; j < 3; j++) {
                CustomerCustomer[i][j] = new Data(7, 20, false);
                CustomerCustomer[i][j].display();
            }
            System.out.println();
        }
        System.out.println();
    }

    //display function
    public  void display()
    {
        System.out.print(distanceInKm + "," + timeInMinutes + "," + acceptVisit +"\t");
    }

    //distance function
    public double getDistance()
    {
        return distanceInKm;
    }

    //time function
    public int getTime()
    {
       return timeInMinutes;
    }


    //main function
    public static void main(String args[])throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //for initial assignments
        initialization();

        //initializing the request array and shelftime array
        int i,j,k;

        for(i=0; i<4; i++)
        {
            System.out.println("Customer " +(i+1) + " enter your choice of restaurant(1,2,3) ");
            j = Integer.parseInt(br.readLine());
            request[i] = j;
            System.out.println("Restaurant " + j + " is requested to specify the shelf time of the order just received ");
            k = Integer.parseInt(br.readLine());
            shelfTime[i] = k;
        }

        //printing the request array
        System.out.println("Customers(1-4) have requested orders from the following restaurants ");
        for(i=0; i<4; i++) {
            System.out.print(request[i] + "\t");

        }
        System.out.println();

        //printing the shelftime array
        System.out.println("Shelf time of the  requested orders from the restaurants are ");
        for(i=0; i<4; i++) {
            System.out.print(shelfTime[i] + "\t");

        }
        System.out.println();
    }
}
