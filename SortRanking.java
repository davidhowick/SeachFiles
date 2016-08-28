package searcheng;

public class SortRanking {
                protected String[][]elems;
                protected int i = 0;
                protected int j = 0;
                
                
                /**
                * construct size of 2D array
                * @param i
                * @param j
                */
                public SortRanking(int i, int j){
                                this.elems = new String[i][j];
                }
                /**
                * Add elements to the 2 dimensional array
                * @param k
                * @param v
                */
                public void add(String k, String v){
                                this.elems[i][j] = k; 
                                j++;
                                this.elems[i][j] = v;
                                i++;
                                j--;
                }
                /**
                * Quick sort algorithm to rank files in ascending order of: file with the most hits
                * @param arr
                * @param left
                * @param right
                * @return
                */
                public int partition(String[][] arr, int left, int right){
                                {
                                      int i = left; 
                                      int j = elems.length-1;
                                      String tmp;
                                      String tmp2;
                                      String pivot = elems[left + right / 2][0];
                                     
                                      while (i <= j) {
                                            while (Integer.valueOf(elems[i][0]) < Integer.valueOf(pivot))
                                                  i++;
                                            while (Integer.valueOf(elems[j][0]) > Integer.valueOf(pivot))
                                                  j--;
                                            if (i <= j) {
                                                  tmp = elems[i][0];
                                                  tmp2 = elems[i][1];
                                                  elems[i][0] = elems[j][0];
                                                  elems[i][1] = elems[j][1];
                                                  elems[j][0] = tmp;
                                                  elems[j][1] = tmp2;
                                                  i++;
                                                  j--;
                                            }
                                      };                           
                                      return i; 
                                }
                }
                                public void quickSort(String arr[][], int left, int right) {
                                      int index = partition(arr, left, right);
                                      if (left < index - 1)
                                            quickSort(arr, left, index - 1);
                                      if (index < right)
                                            quickSort(arr, index, right);
                                }
                
                public void printArr(){
                                for(int i = elems.length-1; i >= 0; i--){
                                                System.out.println(elems[i][0] + " " + elems[i][1] + " -[inside print method 1]");
                                }
                }
                
                /**
                * getter method for the first position of the 2D array
                * @return
                */
                public int GetLeft(){
                                return 0;
                }
                /**
                * getter method for the last position of the 2D array
                * @return
                */
                public int GetRight(){
                                return elems.length;
                }
                /**
                * getter method for the entire array
                * @return
                */
                public String [][] getArray(){
                                return elems;
                }              
}
