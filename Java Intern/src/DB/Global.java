package DB;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Global {
    public  static  String uname,llogin,flag,client;
    public static int flag1=1;
    private static final SessionFactory oursessionf;
    public static long custid;
    public static long prodid;
    public static  long feedid;

    static
    {
        client ="Feed";
        try
        {
            Configuration configuration = new Configuration().configure();
              //configuration.configure();
              oursessionf=configuration.buildSessionFactory();
            }

        catch (Throwable e)
        {
            throw  new ExceptionInInitializerError(e);
        }

        }


    public static Session getSession() {
    try
    {
        return oursessionf.getCurrentSession();

    }
    catch (Exception e)
    {
        return oursessionf.openSession();
    }

    }

    public static void closeFactory() {
    try
    {
        oursessionf.close();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    }
}


