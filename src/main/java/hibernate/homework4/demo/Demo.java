package hibernate.homework4.demo;



import hibernate.homework4.model.*;



public class Demo {
    public static void main(String[] args) throws Exception{

        Session session1 = Session.getSession();
        session1.login("test1", "test");


        session1.logout();
        Session session2 = Session.getSession();
        session2.login("test2", "test");
        session1.login("test1", "test");

    }
}
