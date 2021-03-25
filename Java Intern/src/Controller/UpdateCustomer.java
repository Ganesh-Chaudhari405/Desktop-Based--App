package Controller;

import DB.Customer;
import DB.Global;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.Transaction;

import static DB.Global.custid;

public class UpdateCustomer {


    @FXML
    private Button update;

    @FXML
    private TextField mob;

    @FXML
    private TextField gstno;

    @FXML
    private TextField addr;

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private Label warn;

    @FXML
    private TextField state;

    @FXML
    private RadioButton rdreg;

    @FXML
    private RadioButton rdp;

    @FXML
    private RadioButton rdn;

    @FXML
    private RadioButton rdo;

    @FXML
    private TextField panno;

    @FXML
    void initialize() {
        try
        {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            DB.Customer customer = (Customer) session.createQuery
                    ("from DB.Customer l where l.id=:id and l.d=1").setParameter("id",custid).uniqueResult();
        name.setText(customer.getName());
        mob.setText(customer.getMob());
        email.setText(customer.getEmail());
        addr.setText(customer.getAddr());
        gstno.setText(customer.getGstno());
        state.setText(customer.getState());
        panno.setText(customer.getPanno());
        if(customer.getCusttype().equals("Regular"))
            rdreg.setSelected(true);
        else if (customer.getCusttype().equals("New"))
            rdn.setSelected(true);
        else if (customer.getCusttype().equals("Others"))
            rdo.setSelected(true);
        else if (customer.getCusttype().equals("Premium"))
            rdp.setSelected(true);

        transaction.commit();
        session.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        rdn.setOnMouseClicked(event1 -> {
            rdo.setSelected(false);
            rdp.setSelected(false);
            rdreg.setSelected(false);
        });
        rdreg.setOnMouseClicked(event1 -> {
            rdo.setSelected(false);
            rdp.setSelected(false);
            rdn.setSelected(false);
        });
        rdp.setOnMouseClicked(event1 -> {
            rdo.setSelected(false);
            rdn.setSelected(false);
            rdreg.setSelected(false);
        });
        rdo.setOnMouseClicked(event1 -> {
            rdn.setSelected(false);
            rdp.setSelected(false);
            rdreg.setSelected(false);
        });

        update.setOnAction(event ->
                {
                    String name1= name.getText();
                    String mob1= mob.getText();
                    String email1= email.getText();
                    String addr1 = addr.getText();
                    String gstno1 =gstno.getText();
                    String pan = panno.getText();
                    String stat =state.getText();



                    if(name1.isEmpty())
                        warn.setText("Enter name");

                    else if(mob1.isEmpty())
                        warn.setText("Enter number");

                    else if(mob1.length()!=10)
                        warn.setText("Enter correct Mobile Number");

                    else if(email1.isEmpty())
                        warn.setText("Enter Email");

                    else if(!email1.endsWith("mail.com"))
                        warn.setText("Enter correct Email");

                    else if(addr1.isEmpty())
                        warn.setText("Enter address");

                    else if(gstno1.isEmpty())
                        warn.setText("Enter gstno");
                    else if (pan.isEmpty() | pan.length()==12)
                        warn.setText("Enter PAN No.");

                    else
                    {
                        Session session = Global.getSession();
                        Transaction transaction = session.beginTransaction();

                        DB.Customer customer = (Customer) session.createQuery
                                ("from DB.Customer l where l.id=:id and l.d=1").setParameter("id",custid).uniqueResult();

                        customer.setName(name1);
                        customer.setAddr(addr1);
                        customer.setGstno(gstno1);
                        customer.setMob(mob1);
                        customer.setEmail(email1);
                        customer.setPanno(pan);
                        customer.setState(stat);

                        if(rdreg.isSelected())
                        {
                            customer.setCusttype("Regular");

                        }
                        else if(rdn.isSelected())
                            customer.setCusttype("New");
                        
                        else if (rdp.isSelected())
                            customer.setCusttype("Premium");
                        else if(rdo.isSelected())
                            customer.setCusttype("Others");

                        transaction.commit();
                        session.close();


                        warn.setText("Customer Details Updated Successfully..!!");

                        name.setText("");
                        mob.setText("");
                        email.setText("");
                        addr.setText("");
                        gstno.setText("");
                        state.setText("");
                        panno.setText("");
                        rdreg.setSelected(false);
                        rdo.setSelected(false);
                        rdp.setSelected(false);
                        rdn.setSelected(false);




                    }

                }
                );

    }
}
