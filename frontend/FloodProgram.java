public class FloodProgram
{
public static League myLeague;
public static GUI run;
public static void main(String[] args)
{
myLeague = new League("Happy League");
myLeague.setMaxUser(10);
myLeague.setMinUser(12);
myLeague.addUser(new User("Eli"));
myLeague.addAction(new Action("Field Goal Attempt", 0.5));
run = new GUI(myLeague);
run.drawBoard();
}
public static int myFunction(int turn, float turn2)
{
String a = new String();
String c = new String();
String d = new String();
boolean b = true;
a = a + c + d;
return a;
}
public static int draftFunction(int turn){
return turn%myLeague.getCurrentNumUsers();
}
public static boolean draftPlayer(User u, Player p){
u.addPlayer(p);
return true;
}
public static boolean trade(User u1, Player[] p1, User u2, Player[] p2){
int i,j;
boolean flag2=true;
i=0;
while(i<p1.length){
flag2=dropPlayer(u1,p1[i]);
if(!flag2){    //If the drop was unsuccessful
j=i;
while(j>=0){    //Add p1 back to u1
draftPlayer(u1,p1[j]);
j--;
}
return false;
}
i++;
}
i=0;
while(i<p2.length){
flag2=dropPlayer(u2,p2[i]);
if(!flag2){    //If the drop was unsuccessful
j=i;
while(j>=0){    //Add p2 back to u2
draftPlayer(u2,p2[j]);
j--;
}
j=0;
while(j<p1.length){    //Add p1 to u1
draftPlayer(u1,p1[j]);
j++;
}
return false;
}
i++;
}
i=0;
while(i<p1.length){
flag2=draftPlayer(u2,p1[i]);
if(!flag2){    //If draft was unsuccessful
j=i;
while(j>=0){    //Remove p1 from u2
dropPlayer(u2,p1[j]);
j--;
}
j=0;
while(j<p1.length){    //Add p1 to u1
draftPlayer(u1,p1[j]);
j++;
}
j=0;
while(j<p2.length){    //Add p2 to u2
draftPlayer(u2,p2[j]);
j++;
}
return false;
}
i++;
}
i=0;
while(i<p2.length){
flag2=draftPlayer(u1,p2[i]);
if(!flag2){    //If the drop was unsuccessful
j=i;
while(j>=0){    //Remove p2 from u1
dropPlayer(u1,p2[j]);
j--;
}
j=0;
while(j<p1.length){    //Remove p1 from u2
dropPlayer(u2,p1[j]);
j++;
}
j=0;
while(j<p1.length){    //Add p1 to u1
draftPlayer(u1,p1[j]);
j++;
}
j=0;
while(j<p2.length){    //Add p2 to u2
draftPlayer(u2,p2[j]);
j++;
}
return false;
}
i++;
}
return true;
}
public static boolean dropPlayer(User u, Player p){
u.removePlayer(p);
return true;
}
}
