package com.airtime;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class FileUtil {


  public static File[] getFilesByModifiedDate(File dir){
    File[] files = dir.listFiles();
          
      // Sort files by last modified date
      Arrays.sort(files, new Comparator<File>(){
          public int compare(File f1, File f2)
          {
              return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
          } });
      return files;
  }


  /** Given an array of files, create an array with matching indices that specifies the files sizes in bytes */
  public static long[] getFileSizes(File[] files){
    long[] sizes = new long[files.length];

    for (int i=0; i<files.length; i++)
      sizes[i] = files[i].length();

    return sizes;
  }

}