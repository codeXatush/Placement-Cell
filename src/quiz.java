
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HP
 */
public class quiz extends javax.swing.JFrame {
int c;
int rnum;
    /**
     * Creates new form quiz
     */
    public quiz(int cid,int a) {
        initComponents();
        getContentPane().setBackground(Color.green);
        c=cid;
        rnum = a;
        display();
    }
    void display()
    {
        try
        {
            ArrayList<csquiz> carray = new ArrayList<>();
            String q = "select * from tbquestion";
            myconnection obj = new myconnection();
            PreparedStatement pst = obj.db.prepareStatement(q);
            ResultSet rs = pst.executeQuery();
            JRadioButton op1=null,op2=null,op3=null,op4=null;
            JLabel lop1 = null;
            int x=10,y=20,w=400,h=40;
            ButtonGroup b=null;
            while(rs.next())
            {
                b = new ButtonGroup();
                final csquiz c = new csquiz();
                c.setQid(rs.getInt("id"));
                c.setQname(rs.getString("qname"));
                c.setOption1(rs.getString("option1"));
                c.setOption2(rs.getString("option2"));
                c.setOption3(rs.getString("option3"));
                c.setOption4(rs.getString("option4"));
                c.setCanswer(rs.getInt("correctans"));
                c.setUanswer(-1);
                op1 = new JRadioButton(c.getOption1());
                op2 = new JRadioButton(c.getOption2());
                op3 = new JRadioButton(c.getOption3());
                op4 = new JRadioButton(c.getOption4()); 
                lop1 = new JLabel();
                lop1.setText(c.getQname());
                lop1.setBounds(x,y,w,h);
                y=y+20;
                op1.setBounds(x,y,w,h);
                y=y+20;
                op2.setBounds(x,y,w,h);
                y=y+20;
                op3.setBounds(x,y,w,h);
                y=y+20;
                op4.setBounds(x,y,w,h);
                y=y+20;
                b.add(op1);
                b.add(op2);
                b.add(op3);
                b.add(op4);
                add(lop1);
                add(op1);
                add(op2);
                add(op3);
                add(op4);
                carray.add(c);
                
                op1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       c.setUanswer(1);
                       
                    }
                });
                op2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       c.setUanswer(2);
                       
                    }
                });
                op3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       c.setUanswer(3);
                       
                    }
                });
                op4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       c.setUanswer(4);
                       
                    }
                });
                
            }
            JButton bsubmit = new JButton("SUBMIT");
                bsubmit.setBounds(x, y+40, w, h);
                add(bsubmit);
                bsubmit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int cans=0,incans=0,unatt=0;
                        for(int i=0;i<carray.size();i++)
                        {
                            csquiz ca = null;
                            ca = (csquiz)carray.get(i);
                            try
                            {
                                myconnection obj = new myconnection();
                                String q = "insert into tbresult(rnum,cid,qid,uanswer,canswer,examdate)values(?,?,?,?,?,?)";
                                PreparedStatement pst = obj.db.prepareStatement(q);
                                pst.setInt(1,rnum);
                                pst.setInt(2,c);
                                pst.setInt(3,ca.getQid());
                                pst.setInt(4,ca.getUanswer());
                                pst.setInt(5,ca.getCanswer());
                                Date d = new Date();
                                pst.setString(6,""+d);
                                pst.executeUpdate();
                                 
                                if(ca.getUanswer()==(ca.getCanswer()))
                                {
                                    cans++;
                                }
                                else if(ca.getUanswer()!=(ca.getCanswer()) && ca.getUanswer()!=-1)
                                {
                                    incans++;
                                }
                                else if(ca.getUanswer()==-1)
                                {
                                    unatt++;
                                }
                                
                            }
                            catch(Exception f)
                            {
                                System.out.println(f.getMessage());
                            }
                            System.out.println("Cname is "+ca.getQname()+"Answer is "+ca.getUanswer());
                        }
                        try
                        {
                           
                                myconnection obj1 = new myconnection();
                                String s = "insert into tbaggregate(rnum,cid,correctans,incorrectans,unattempted,testdate)values(?,?,?,?,?,?)";
                                PreparedStatement pst1 = obj1.db.prepareStatement(s);
                                pst1.setInt(1,rnum);
                                pst1.setInt(2,c);
                                pst1.setInt(3,cans);
                                pst1.setInt(4,incans);
                                pst1.setInt(5,unatt);
                                Date d1 = new Date();
                                pst1.setString(6,""+d1);
                                pst1.executeUpdate();
                        }
                        catch(Exception g)
                        {
                            System.out.println(g.getMessage());
                        }
                }
            });
                
                
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(quiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(quiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(quiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(quiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new quiz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
