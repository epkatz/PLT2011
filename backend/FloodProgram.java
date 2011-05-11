public class FloodProgram
{
public static League myLeague;
public static GUI run;
public static void main(String[] args)
{
myLeague = new League("Teaching Assistant League");
myLeague.addUser(new User("Alfred Aho"));
myLeague.addUser(new User("Steven Edwards"));
myLeague.addUser(new User("Anargyros Papageorgiou"));
myLeague.addUser(new User("Adam Cannon"));
myLeague.addUser(new User("Paul Blaer"));
myLeague.addAction(new Action("grading error", -1.0));
myLeague.addAction(new Action("help student", 0.75));
myLeague.addAction(new Action("write solution manual", 2.0));
myLeague.addAction(new Action("proctor test", 1.5));
myLeague.addAction(new Action("grade homework", 0.5));
myLeague.addAction(new Action("grade test", 1.0));
myLeague.addAction(new Action("hold office hours", 0.5));
myLeague.addPlayer(new Player("Austin Reiter", "teacher"));
myLeague.addPlayer(new Player("Aditya Apurba Bir", "grader"));
myLeague.addPlayer(new Player("Kwan-I Lee", "grader"));
myLeague.addPlayer(new Player("Tianju Wang", "head"));
myLeague.addPlayer(new Player("Hemanth Murthy", "grader"));
myLeague.addPlayer(new Player("Rupalbahen Shah", "teacher"));
myLeague.addPlayer(new Player("Kapil Verma", "head"));
myLeague.addPlayer(new Player("Xiaowei Zhang", "grade"));
myLeague.addPlayer(new Player("Aanchal Arora", "teacher"));
myLeague.addPlayer(new Player("Pranjal Gupta", "head"));
myLeague.addPlayer(new Player("Kritika Kaul", "grader"));
myLeague.addPlayer(new Player("Neha Srivastava", "grader"));
myLeague.addPlayer(new Player("Aarthi Venkataramanan", "grader"));
myLeague.addPlayer(new Player("Ang Cui", "teacher"));
myLeague.addPlayer(new Player("Yi Zhang", "grader"));
myLeague.addPlayer(new Player("Mukund Jha", "head"));
myLeague.addPlayer(new Player("Priyank Singh", "grader"));
myLeague.addPlayer(new Player("Karthik Kumar Srivatsa", "grader"));
run = new GUI(myLeague);
run.drawBoard();
}
public static boolean testLimit(int num)
{
boolean flag = false;
if(num < 4)
{
flag = true;
}
else
{
GUI.alert("Too many people","You have enough TA's");
}
return flag;
}
public static boolean draftPlayer(User u, Player p)
{
boolean size;
boolean value = false;
int i;
String teacher = new String();
String TA = new String();
teacher = u.getName();
TA = p.getName();
i = u.getNumPlayers();
i = i + 1;
size = testLimit(i);
if(size)
{
if(TA.equals("Hemanth Murthy") && !teacher.equals("Alfred Aho"))
{
GUI.error("Draft Error","Hemanth can only be added to aho!");
}
else
{
u.addPlayer(p);
value = true;
}
}
return value;
}
public static boolean dropPlayer(User u, Player p)
{
boolean value = true;
u.removePlayer(p);
return value;
}
public static int draftFunction(int turn){
return turn%myLeague.getCurrentNumUsers();
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
}