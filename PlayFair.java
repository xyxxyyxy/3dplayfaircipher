import java.util.*;

public class PlayFair {

   private final String letters = "0123456789abcdefghijklmnopqrstuvwxyz!\"#$%&'()*+,-./:;<=>?@[\\]^_|";
   private char[][] floor1 = new char[4][4];
   private char[][] floor2 = new char[4][4];
   private char[][] floor3 = new char[4][4];
   private char[][] floor4 = new char[4][4];

   public void generateMatrices(String key) {
      //getting rid of repeated chars
      String longAssString = key.toLowerCase() + letters;
      Set<Character> tempSet = new LinkedHashSet<Character>();
      for (char c: longAssString.toCharArray()) {
         tempSet.add(c);
      }

      //populating matrices
      int j = 0;
      int i = 0;
      int counterI = 0;
      for (char c: tempSet) {
         if (j!=0 && j % 4 == 0){
            j = 0;
            i++;
         }
         if (i != 0 && i % 4 == 0){
            i = 0;
            counterI++;
         }

         if (counterI==0)
         floor1[i][j] = c;
         else if (counterI==1)
         floor2[i][j] = c;
         else if (counterI==2)
         floor3[i][j] = c;
         else
         floor4[i][j] = c;
         j++;
      }
      System.out.println("generating matrices");
   }

   public void printMatrices(){
      for(int i = 0; i < 4; i++){
         for(int j = 0; j < 8; j++){
            if (j<4)
            System.out.print(floor1[i][j] + "  ");
            if (j==4)
            System.out.print("  ");
            if (j>=4)
            System.out.print(floor2[i][j-4] + "  ");

         }
         System.out.println("\n");
      }
      System.out.println();
      for(int i = 0; i < 4; i++){
         for(int j = 0; j < 8; j++){
            if (j<4)
            System.out.print(floor3[i][j] + "  ");
            if (j==4)
            System.out.print("  ");
            if (j>=4)
            System.out.print(floor4[i][j-4] + "  ");
         }
         System.out.println("\n");
      }
   }

   public void encrypt(String toEncrypt) {
      String encrypted = "";
      List<Character> chars = new ArrayList<Character>();
      toEncrypt = toEncrypt.toLowerCase().replace(" ", "");
      for (char c: toEncrypt.toCharArray()) {
         chars.add(c);
      }
      chars.add('X');
      chars.add('X');
      chars.add('X');

      while(chars.get(0)!='X'){
         boolean flag = false;
         if(chars.get(1)=='X'){
            if(chars.get(0)=='x'){
               chars.remove(2);
               chars.remove(1);
               chars.add(1, 'z');
               chars.add(2, 'x');
            }
            else{
               chars.remove(2);
               chars.remove(1);
               chars.add(1, 'x');
               chars.add(2, 'z');
            }
            flag = true;
         }
         else if(chars.get(2)=='X'){
            if(chars.get(1)=='x'){
               chars.remove(2);
               chars.add(2, 'z');
            }
            else{
               chars.remove(2);
               chars.add(2, 'x');
            }
            flag = true;
         }

         if(chars.get(0)==chars.get(1)){
            if(chars.get(0)=='x')
            chars.add(1, 'z');
            else
            chars.add(1, 'x');
         }
         else if(chars.get(1)==chars.get(2)){
            if(chars.get(1)=='x')
            chars.add(2, 'z');
            else
            chars.add(2, 'x');
         }

         System.out.print(chars.get(0));
         System.out.print(chars.get(1));
         System.out.print(chars.get(2));
         System.out.print(" ");
         int[] floor = new int[3];
         int[] row = new int[3];
         int[] column = new int[3];
         char[] group = new char[3];
         group[0] =chars.get(0);
         group[1] =chars.get(1);
         group[2] =chars.get(2);
         for(int k= 0; k<3; k++){
            for(int i= 0; i<4; i++){
               for(int j= 0; j<4; j++){
                  floor[k]= whichMatrix (group[k]);
                  if (whichMatrix (group[k])==1){
                     if(group[k]==floor1[i][j]){
                        row[k]= i;
                        column[k]= j;
                     }
                  }
                  else if (whichMatrix (group[k])==2){
                     if(group[k]==floor2[i][j]){
                        row[k]= i;
                        column[k]= j;
                     }
                  }
                  else if (whichMatrix (group[k])==3){
                     if(group[k]==floor3[i][j]){
                        row[k]= i;
                        column[k]= j;
                     }
                  }
                  else{
                     if(group[k]==floor4[i][j]){
                        row[k]= i;
                        column[k]= j;
                     }
                  }
               }
            }
         }

         if(floor[2]==1){
            encrypted += floor1[row[0]][column[1]];
         }
         if(floor[2]==2){
            encrypted += floor2[row[0]][column[1]];
         }
         if(floor[2]==3){
            encrypted += floor3[row[0]][column[1]];
         }
         if(floor[2]==4){
            encrypted += floor4[row[0]][column[1]];
         }

         if(floor[0]==1){
            encrypted += floor1[row[1]][column[2]];
         }
         if(floor[0]==2){
            encrypted += floor2[row[1]][column[2]];
         }
         if(floor[0]==3){
            encrypted += floor3[row[1]][column[2]];
         }
         if(floor[0]==4){
            encrypted += floor4[row[1]][column[2]];
         }

         if(floor[1]==1){
            encrypted += floor1[row[2]][column[0]];
         }
         if(floor[1]==2){
            encrypted += floor2[row[2]][column[0]];
         }
         if(floor[1]==3){
            encrypted += floor3[row[2]][column[0]];
         }
         if(floor[1]==4){
            encrypted += floor4[row[2]][column[0]];
         }
         encrypted +=" ";

         chars.remove(2);
         chars.remove(1);
         chars.remove(0);
         if(flag)
         break;
      }
      System.out.println("");
      System.out.print("encrypted: ");
      System.out.println(encrypted);
   }

   public void decrypt(String toDecrypt) {
      String decrypted = "";
      List<Character> chars = new ArrayList<Character>();
      toDecrypt = toDecrypt.toLowerCase().replace(" ", "");
      for (char c: toDecrypt.toCharArray()) {
         chars.add(c);
      }

      while(!chars.isEmpty()){

         System.out.print(chars.get(0));
         System.out.print(chars.get(1));
         System.out.print(chars.get(2));
         System.out.print(" ");
         int[] floor = new int[3];
         int[] row = new int[3];
         int[] column = new int[3];
         char[] group = new char[3];
         group[0] =chars.get(0);
         group[1] =chars.get(1);
         group[2] =chars.get(2);
         for(int k= 0; k<3; k++){
            for(int i= 0; i<4; i++){
               for(int j= 0; j<4; j++){
                  floor[k]= whichMatrix (group[k]);
                  if (whichMatrix (group[k])==1){
                     if(group[k]==floor1[i][j]){
                        row[k]= i;
                        column[k]= j;
                     }
                  }
                  else if (whichMatrix (group[k])==2){
                     if(group[k]==floor2[i][j]){
                        row[k]= i;
                        column[k]= j;
                     }
                  }
                  else if (whichMatrix (group[k])==3){
                     if(group[k]==floor3[i][j]){
                        row[k]= i;
                        column[k]= j;
                     }
                  }
                  else{
                     if(group[k]==floor4[i][j]){
                        row[k]= i;
                        column[k]= j;
                     }
                  }
               }
            }
         }

         if(floor[1]==1){
            decrypted += floor1[row[0]][column[2]];
         }
         if(floor[1]==2){
            decrypted += floor2[row[0]][column[2]];
         }
         if(floor[1]==3){
            decrypted += floor3[row[0]][column[2]];
         }
         if(floor[1]==4){
            decrypted += floor4[row[0]][column[2]];
         }

         if(floor[2]==1){
            decrypted += floor1[row[1]][column[0]];
         }
         if(floor[2]==2){
            decrypted += floor2[row[1]][column[0]];
         }
         if(floor[2]==3){
            decrypted += floor3[row[1]][column[0]];
         }
         if(floor[2]==4){
            decrypted += floor4[row[1]][column[0]];
         }

         if(floor[0]==1){
            decrypted += floor1[row[2]][column[1]];
         }
         if(floor[0]==2){
            decrypted += floor2[row[2]][column[1]];
         }
         if(floor[0]==3){
            decrypted += floor3[row[2]][column[1]];
         }
         if(floor[0]==4){
            decrypted += floor4[row[2]][column[1]];
         }

         chars.remove(2);
         chars.remove(1);
         chars.remove(0);
      }
      System.out.println("");
      System.out.print("decrypted: ");
      System.out.println(removeX(decrypted));
   }

   public int whichMatrix (char letter){
      for(int i= 0; i<4; i++){
         for(int j= 0; j<4; j++){
            if(floor1[i][j]==letter){
               return 1;
            }
            if(floor2[i][j]==letter){
               return 2;
            }
            if(floor3[i][j]==letter){
               return 3;
            }
            if(floor4[i][j]==letter){
               return 4;
            }
         }
      }

      return 0;
   }

   public String removeX (String s){
      String result = "";
      List<Character> chars = new ArrayList<Character>();
      for (char c: s.toCharArray()) {
         chars.add(c);
      }
      for(int i = s.length()-1; i > 2; i--){
         if (chars.get(i)==chars.get(i-2) && chars.get(i-1)=='x'){
            chars.remove(i-1);
         }
         if (chars.get(i)==chars.get(i-2) && chars.get(i-1)=='z' && chars.get(i)=='x'){
            chars.remove(i-1);
         }
      }
      if (s.charAt(s.length()-2)=='x' && s.charAt(s.length()-1)=='z'){
         chars.remove(chars.size()-1);
         chars.remove(chars.size()-1);
      }
      if (s.charAt(s.length()-1)=='x'){
         chars.remove(chars.size()-1);
      }
      for (char c: chars) {
         result+=c;
      }
      return result;
   }



   /* Test */
   public static void main(String[] args) {
      PlayFair caca = new PlayFair();
      caca.generateMatrices("FRIENDS4V@TJ_201.C");
      System.out.println("");
      caca.printMatrices();
      System.out.println("");
      caca.encrypt("BALLOON");
      System.out.println("");
      caca.encrypt("HELLOWORLDS");
      System.out.println("");
      caca.encrypt("MASTI_M.TECH@NITJ.2012");

      System.out.println("");
      caca.decrypt("abl )k7 &64");
      System.out.println("");
      caca.decrypt("hc1 )oc lc1 znd");
      System.out.println("");
      caca.decrypt("_g8 tf0 _3g ceh vsr hvi 012 &sy");
   }

}
