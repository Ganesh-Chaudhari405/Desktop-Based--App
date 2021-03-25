package Controller;

import DB.Customer;
import DB.Feedback;
import DB.Global;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.List;

import static DB.Global.feedid;

public class UpdateFeedback {
    @FXML
    private Label lblWarn;
    @FXML
    private DatePicker date;
    @FXML
    private TextField txtMobile, txtEmail, txtGstno, txtPanno, txtfeed;
    @FXML
    private TextArea txtAddr, txtfeedback;
    @FXML
    private Slider slider;
    @FXML
    private TextField txtState;
    @FXML
    private Button  btnfeed;
    @FXML
    private RadioButton rdRegular, rdNew, rdPremium, rdOthers;

    @FXML
    private ComboBox<String> comboCustomer;
    private ObservableList<String> custlist = FXCollections.observableArrayList();
    private ObservableList<String> data = FXCollections.observableArrayList();

    public void initialize()
    {
        date.setValue(LocalDate.now());
        setData();

        Session session3 = Global.getSession();
        Transaction transaction3 = session3.beginTransaction();
        Feedback feedback1 = (Feedback) session3.createQuery
                ("from DB.Feedback g where g.id=:id and g.d=1").setParameter("id",feedid).uniqueResult();
        transaction3.commit();
        session3.close();

        comboCustomer.getItems().addAll(custlist);



        comboCustomer.setOnAction(event ->
        {
            try {
                String category = comboCustomer.getSelectionModel().getSelectedItem();
                if (category.isEmpty()) {
                    txtAddr.setText("");
                    txtMobile.setText("");
                    txtState.setText("");
                    txtGstno.setText("");
                    txtPanno.setText("");
                    txtEmail.setText("");
                }

                else {
                    Session session = Global.getSession();
                    Transaction transaction = session.beginTransaction();
                    Customer customer = (Customer) session.createQuery("from DB.Customer s where s.name=:id and s.d=1").setParameter("id", category).uniqueResult();
                    transaction.commit();
                    session.close();
                    if (customer != null) {
                        txtAddr.setText(customer.getAddr());
                        txtMobile.setText(customer.getMob());
                        txtState.setText(customer.getState());
                        txtGstno.setText(customer.getGstno());
                        txtPanno.setText(customer.getPanno());
                        txtEmail.setText(customer.getEmail());


                        switch (customer.getCusttype()) {
                            case "Regular":
                                rdRegular.setSelected(true);
                                break;
                            case "Others":
                                rdOthers.setSelected(true);
                                break;
                            case "Premium":
                                rdPremium.setSelected(true);
                                break;
                            case "New":
                                rdNew.setSelected(true);
                                break;
                        }


                    } else {
                        txtAddr.setText("");
                        txtMobile.setText("");
                        txtState.setText("");
                        txtGstno.setText("");
                        txtPanno.setText("");
                        txtEmail.setText("");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        btnfeed.setOnAction(event ->
        {
            LocalDate date1 = date.getValue();
            String feed1 = txtfeedback.getText();
            String rating1 = txtfeed.getText();
            String ctype1 = comboCustomer.getSelectionModel().getSelectedItem();
            long cid = checkCustomer();
            if (txtfeedback.getText().equals(""))
                lblWarn.setText("Please enter feedback");
            else
            {
                try
                {
                    Session session = Global.getSession();
                    Transaction transaction = session.beginTransaction();
                    DB.Feedback feedback = new DB.Feedback();
                    if(cid != 0)
                    {
                        DB.Customer customer = (Customer) session.createQuery("from DB.Customer s where s.name=:id and s.d=1").setParameter("id", comboCustomer.getSelectionModel().getSelectedItem()).uniqueResult();
                        feedback.setCustomer(customer);

                    }
                    else
                    {
                        String txtCname1 = comboCustomer.getSelectionModel().getSelectedItem();
                        String txtMobile1 = txtMobile.getText();
                        String txtEmail1 = txtEmail.getText();
                        String txtAddr1 = txtAddr.getText();
                        String txtGstno1 = txtGstno.getText();
                        String txtPanno1 = txtPanno.getText();
                        String txtState1 = txtState.getText();
                        if (txtCname1.equals(""))
                            lblWarn.setText("Please enter customer name");
                        else
                        {
                            try
                            {
                                Customer customer = new Customer();
                                customer.setState(txtState1);
                                customer.setName(txtCname1);
                                customer.setMob(txtMobile1);
                                customer.setEmail(txtEmail1);
                                customer.setAddr(txtAddr1);
                                customer.setGstno(txtGstno1);
                                customer.setPanno(txtPanno1);
                                if(rdRegular.isSelected())
                                    customer.setCusttype("Regular");
                                else if(rdOthers.isSelected())
                                    customer.setCusttype("Others");
                                else if (rdPremium.isSelected())
                                    customer.setCusttype("Premium");
                                else
                                    customer.setCusttype("New");
                                if (txtState1.equals(""))
                                    customer.setState("27");
                                else
                                    customer.setState(txtState1);
                                customer.setD(1);
                                session.persist(customer);
                                feedback.setCustomer(customer);
                            }
                            catch (PersistenceException e)
                            {
                                lblWarn.setText("Please try again..!!");
                            }
                            catch (Exception e)
                            {
                                lblWarn.setText("Please try again");
                            }
                        }
                    }
                    feedback.setName(ctype1);
                    feedback.setFeedmsg(feed1);
                    feedback.setRating(rating1);
                    feedback.setDate(date1);
                    feedback.setD(1);
                    session.persist(feedback);
                    transaction.commit();
                    session.close();
                    lblWarn.setText("Feedback  Added...!!");
                    txtfeedback.setText("");
                }

                catch (Exception e)
                {
                    lblWarn.setText("Please try again..!!");
                }
            }
        });

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {

            txtfeed.setText(Double.toString(newValue.intValue()));

        });
    }

    private void setData()
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        List<Customer> clist = session.createQuery("from DB.Customer s where s.d=1 order by s.name asc").list();
        for (Customer customer : clist)
        {
            custlist.add(customer.getName());
        }

        transaction.commit();
        session.close();
    }

    private long checkCustomer()
    {
        try
        {
            String cname = comboCustomer.getSelectionModel().getSelectedItem();
            if(cname.equals(""))
                return 0;
            else
            {
                Session session = Global.getSession();
                Transaction transaction = session.beginTransaction();
                DB.Customer customer = (Customer) session.createQuery("from DB.Customer s where s.name=:id and s.d=1").setParameter("id", cname).uniqueResult();
                if(customer != null)
                {
                    transaction.commit();
                    session.close();
                    return customer.getId();
                }
                transaction.commit();
                session.close();
                return 0;
            }
        }
        catch (Exception e)
        {
            return 0;
        }
    }


}
