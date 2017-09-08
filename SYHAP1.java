
package project.data;

import java.util.*; 
import java.util.Scanner;

/** * CS 3345 // always use this type of header block
 * Project 1 
 *
 * @author syed hassan 
 *
 * date 2/15/2017
 *
 */
class Node {
    public int size; 
    public int timeToDepart;
    public int address;
    public boolean occupied;
    public Node next; 
    public Node(int segmentSize, int timeToLeave, boolean type,Node x)
    {
     size=segmentSize;
     timeToDepart=timeToLeave;
     occupied=type;
     next=x;
    }
}
class Simulation{
    private int memory;
    private Node head;
     public static int type=0;
        public int low=0;
        public boolean done=false;
         public double count;
    public double count2;
    public double count3;
    public double count4;
    public double count5;
    public double size;
    private boolean placed (int size,int timeToLeave)//function for placing the segment
    {
        int remain;
        int size2;
        if(head.next==null&&head.occupied==false)//If THEIR IS ONLY ONE NODE
        {
            Node x =new Node(memory-size,0,false,null);
           head.occupied=true;
           head.size=size;
           head.next=x;
           head.address=0;
           head.timeToDepart=timeToLeave;
           x.address=head.address+size;
            return true;
        }
         Node pos=head;
        if(type==1)//first first policy
        {
        while(pos!=null)
        {
            if(pos.occupied==false&&pos.size>=size)//checking hole with size greater than placement
            {
                count5++;//counting the number of checking
                remain=pos.size-size;
                if(remain>0)
                {
               Node y=new Node(pos.size-size,0,false,pos.next);//create a hole with the remaing asize
                pos.occupied=true;
                pos.size=size;
                pos.next=y;
                pos.timeToDepart=timeToLeave;
                y.address=pos.address+size; 
                }
                else
                {
                    pos.occupied=false;
                    pos.timeToDepart=timeToLeave;
                }
                return true;
            }
            pos=pos.next;
        }
        }
        if(type==2)//policy for best fit
        {
            size2=pos.size;
          while(pos!=null)
          {
           if(pos.size>=size&&pos.size<=size2&&pos.occupied==false)
           {
               size2=pos.size;
           }
           pos=pos.next;
          }
          pos=head;
          while(pos!=null)
          {
              if(pos.size==size2&&pos.occupied==false)
              {
                  remain=size2-size;
                if(remain>0)
                {
               Node y=new Node(remain,0,false,pos.next);
                pos.occupied=true;
                pos.size=size;
                pos.next=y;
                pos.timeToDepart=timeToLeave;
                y.address=pos.address+size; 
                }
                else
                {
                    pos.occupied=false;
                    pos.timeToDepart=timeToLeave;
                }
                return true;
          }
               pos=pos.next;
          }
            }
    return false;
    }
    private void removeSegmentsDueToDepart(int time)
    {
       Node postion=head;
       Node pos2=postion.next;
       Node pos3=postion;
       while(postion!=null)//making the head hole
       {
           if(postion.occupied==true&&postion.timeToDepart<=time)
           {
               postion.occupied=false;
           }
           postion=postion.next;
       }
       postion=head;
       while(postion!=null){
         if(pos2!=null)
         {    
           if(postion.occupied==false&&pos2.occupied==false)
         {                                //swapping holes with segment
             if(pos2.next!=null){
              postion.next=pos2.next;
              pos2.next=pos2.next.next;
             }
             else//swapiing at last place
             {
                 pos3.next=pos2;
                 pos2.next=postion;
                 postion.next=null;
             }
         }   
         }
         pos3=postion;
           postion=postion.next;
           if(pos2!=null){
           pos2=pos2.next;
           }
       }
    }
   
        
    
    
    public void memory(int x)//initial node
    {
        memory =x;
        head =new Node(memory,0,false,null);
    }
    public Simulation()
    {
        
    }
    public int place( Node seg, int timeCurrent)//time when placement is done
    {
        int time =timeCurrent+1;
        stat();
        count3++;
        size+=seg.size*seg.timeToDepart;
        removeSegmentsDueToDepart(time);
        while(!placed(seg.size,seg.timeToDepart+time))
        {
            count4++;
            time++;
            stat();
            removeSegmentsDueToDepart(time);
        }
        return time;
    }
    public void display()//displaying the segment contents and holes content
    {
      Node position= head;
      while(position!=null)
      {
          if(position.occupied==true)
          {
         System.out.println("Segement  "+position.address+" "+position.size+"  "+position.timeToDepart);
          }
          position=position.next;
      }
       position=head;
       while(position!=null)
      {
          if(position.occupied==false)
          {
         System.out.println(" H"+position.address+" "+position.size);
          }
          position=position.next;
      }
    }
    private void stat()//calculation the statistic
    {
     Node pos=head;
     while(pos!=null)
     {
         if(pos.occupied==true)
         {
             count++;//numer of seg
         }
         if(pos.occupied==false)
         {
             count2++; //number of holes
         }
         pos=pos.next;
     }     
                 
             
    }
}
public class ProjectData {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean notDone = true;
        int time=0;
        Node x;
        Simulation y=new Simulation();
        while(notDone) { 
            String line = in.nextLine();
            String [] tokens = line.split(" ");
            switch(tokens[0]) { 
                case "FF":
                    System.out.println("FIRST FIT");
                   y.type=1;
                    break;
                case "C":
                    System .out.println(tokens[0]);
                    System.out.println(tokens[1]);
                    y.memory((Integer.parseInt(tokens[1])));
                        break;
                case "Bf":
                    System.out.println("Best first");
                    y.type=2;
                    break;
                case "P":
                    x=new Node((Integer.parseInt(tokens[1])),(Integer.parseInt(tokens[2])),true,null);
                    time=y.place(x, time);
                    break;
                case "R":
                    System.out.println("Reported time  "+time);
                    y.display();
                    break;
                case "S":
                    System.out.println("Average number of segment  "+y.count/time);
                    System.out.println("Average number of holes  "+y.count2/time);
                    System.out.println("Faliure percentage rate "+(y.count4/y.count3)*100);
                    System.out.println("Total use of memory  "+y.size/time);
                    System.out.println("Average probe is   "+y.count5/y.count3);
                    
                    break;
                case "E":
                    notDone=false;
                    break;      

            }
        }
    }
    
}
