package com.xav;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class Utility {

    //to get the distance matrixs
    public static double[][] extractSubArray(double[][] array, int startIndexRow, int endIndexRow, int startIndexCol, int endIndexCol)
    {
        double[][] extractedSubArray = new double[(endIndexRow-startIndexRow)+1][(endIndexCol-startIndexCol)+1];
        int i,j;
        int m,n;
        for(i=startIndexRow , m=0 ; i<=endIndexRow; i++,m++)
        {
            for(j=startIndexCol, n=0 ; j<=endIndexCol; j++,n++)
            {
                extractedSubArray[m][n] = array[i][j];
            }
        }

        return extractedSubArray;
    }

    //generating all subsets
    public static <T>Set<Set<T>> getAllSubset(Set<T> set) {

        Set<Set<T>> allSubsets = new HashSet<Set<T>>();

        for(int i=1; i<=set.size(); i++)
        {
            allSubsets.addAll(Sets.combinations(set, i));
        }

        return allSubsets;
    }

    //location to gapedorder


}
