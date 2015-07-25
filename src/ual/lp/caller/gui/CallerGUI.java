/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import ual.lp.caller.mgr.CallerMGR;
import ual.lp.caller.rmi.CallerInf;
import ual.lp.caller.rmi.ClientRMI;
import ual.lp.caller.utils.Config;
import ual.lp.exceptions.BadConfigurationException;
import ual.lp.exceptions.NoTicketsException;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;
import ual.lp.server.rmi.ServerInf;

/**
 *
 * @author Divanio Silva
 */
public class CallerGUI extends javax.swing.JFrame {

    static final Logger callerLog = Logger.getLogger("callerLogger");
    static final Logger debugLog = Logger.getLogger("debugLogger");
    private Config config;
    private Employee employee;
    private CallerMGR callerMGR;
    private ClientRMI clientRMI;
    private ServerInf remoteObject;
    Ticket ticket, ticketLast;
    private CallerInf callerInf;
    private List<Employee> employees;
    private String closeCaller;
    private Thread serviceThread;
    private String myIP;
    private String serverIP;

    /**
     * Creates new form CallerPanel
     */
    public CallerGUI() {
        this.serviceThread = new Thread(new ServiceThread(this));
        this.getContentPane().setBackground(Color.white);
        config = new Config();
        employee = config.getEmployee();
        myIP = config.getMyIP();
        serverIP = config.getServerIP();

        try {
//            this.setIconImage(new ImageIcon(getClass().getResource("UAL_Logo.png")).getImage());
            this.setIconImage(new ImageIcon(getClass().getResource("logo.jpg")).getImage());
        } catch (Exception e) {
            debugLog.info("Não consegui encontrar o icon.\n" + e);
        }

        this.setLocationRelativeTo(null);
        this.employees = new LinkedList<>();

        initComponents();

        try {

            this.clientRMI = new ClientRMI(this);
            initData();
        } catch (RemoteException e) {
            System.err.println("O server esta off-line.\n" + e.getMessage());
            callerLog.error("O server esta off-line.", e);
            JOptionPane.showMessageDialog(this, "O servidor esta off-line.\nContacte o administrador do sistema.");
            System.exit(1);
        } catch (NotBoundException e) {
            System.err.println("Caiu na notBound" + e.getMessage());
            callerLog.error("Caiu na notBound", e);
            System.exit(1);
            JOptionPane.showMessageDialog(this, "O servidor esta off-line.\nContacte o administrador do sistema.");
        } catch (NullPointerException en) {
            System.err.println("Deu nullPointer" + en.getMessage());
            callerLog.error("Deu nullPointer", en);
            JOptionPane.showMessageDialog(this, "O servidor esta off-line.\nContacte o administrador do sistema.");
            System.exit(1);
        } catch (BadConfigurationException bad) {
            callerLog.error("O caller apresenta configurações inválidas.", bad);
            JOptionPane.showMessageDialog(this, "As configurações do posto de trabalho não foram"
                    + "\n não foram feitas correctamente.\nContacte o administrador do sistema.");
            System.exit(1);
        }
    }

    /**
     *
     * @param operation
     */
    //    public void setDefaultCloseOperation(int operation) { 
    //        System.out.println("OLA");
    ////        JOptionPane.showMessageDialog("Olá", this);
    //        super.setDefaultCloseOperation(operation);
    //        
    //    }
    //    public int pjcMethod() {
    //
    //        System.out.println("OLE");
    //        debugLog.info("OLE");
    //        return EXIT_ON_CLOSE;
    //    }
    public void initData() throws RemoteException, BadConfigurationException {

        jLabelEmpName.setText(employee.getName());
        jLabelEmpDepartment.setText(employee.getDepartment().getName());
        jLabelEmpDesk.setText(String.valueOf(employee.getDeskNumber()));
        employee.setCallerInf(this.callerInf);
        remoteObject.connect(employee);
        jLabelActualTicket.setText("");
        jLabelLastTicket.setText("");
        serviceThread.start();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (jLabelActualTicket.getText().equals("")) {
                    System.exit(0);
                } else {
                    try {
                        if (jComboBoxColleagues.getSelectedItem() != "") {
//                System.out.println("Transferir para " + jComboBoxColleagues.getSelectedItem());
                            //correr o array dos emp para encontrar o que quero fazer a transf.
                            for (int i = 0; i < employees.size(); i++) {
                                if (employees.get(i).getName().equals(jComboBoxColleagues.getSelectedItem())) {
//                                    employees.get(i);
                                    System.out.println("Encontrei o gajo na lista.\nIrei transferir para " + employees.get(i).getName());
                                    System.out.println(i);
                                    ticket.setTransferId(employees.get(i).getEmpNumber());
                                    remoteObject.transferTicket(ticket);
                                    break;

                                }
                            }
                            System.exit(0);

                        } else {

                            remoteObject.closeTicket(ticket);
                            System.exit(0);

                        }
                    } catch (RemoteException ex) {

                        callerLog.error("Erro ao encerrar o ticket quando o caller é fechado", ex);
                        System.exit(1);
                    }

//                try {
//                    remoteObject.closeTicket(ticket);
//                    System.out.println("Estou a fechar o ticket "+ ticket);
//                     debugLog.info("Fechei o programa.");
//                     System.exit(0);
//                } catch (Exception e) {
//                    callerLog.error("Erro ao encerrar o ticket quando o caller é fechado."+e);
//                    System.exit(1);
//                }
                }
            }
        }
        );

        //chama o connect do rmi e mando o emp como argumento.
    }

    /**
     * Faz o update da lista de employees do mesmo departamento quando existem
     * alterações
     *
     * @param employees
     */
    public void updateEmployees(List<Employee> employees) {
//        this.employees = null;
        this.employees = employees;
        jComboBoxColleagues.removeAllItems();
        jComboBoxColleagues.addItem("");

        System.out.println("Lista dos employees recebidas do server:");
        for (int i = 0; i < employees.size(); i++) {
            System.out.println(employees.get(i).getName() + " e id " + employees.get(i).getEmpNumber());
        }

//        for (int i = 1; i < jComboBoxColleagues.getItemCount(); i++) {
//            System.out.println("Irei remover "+ jComboBoxColleagues.getItemAt(i));
//            
//            jComboBoxColleagues.removeItemAt(i);
//            
//
//        }
        for (Employee emp : employees) {
            if (!(emp.getName().equals(employee.getName()))) {
                jComboBoxColleagues.addItem(emp.getName());
                callerLog.debug("Irei adicionar " + emp.getName());
            }

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

        jPopupAmin = new javax.swing.JPopupMenu();
        jMenuIResetQueu = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxColleagues = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelEmpName = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelEmpDepartment = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelEmpDesk = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabelLastTicket = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabelActualTicket = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButtonRefresh = new javax.swing.JButton();
        jButtonCallNext = new javax.swing.JButton();

        jMenuIResetQueu.setText("Reset");
        jMenuIResetQueu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuIResetQueuActionPerformed(evt);
            }
        });
        jPopupAmin.add(jMenuIResetQueu);

        setTitle("UAL iSenhas");
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(222, 222, 222));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(222, 222, 222));
        setMinimumSize(new java.awt.Dimension(492, 358));
        setName("iSenhas - Caller"); // NOI18N
        setPreferredSize(new java.awt.Dimension(492, 358));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 119, 163), 1, true), "Transferências", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 119, 163)));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel3.setText("Colegas");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        jComboBoxColleagues.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jComboBoxColleagues.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        jComboBoxColleagues.setPreferredSize(new java.awt.Dimension(35, 24));
        jComboBoxColleagues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxColleaguesActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBoxColleagues, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 140, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 220, 160, 90));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 119, 163)), "Colaborador", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 119, 163)));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Nome:");

        jLabelEmpName.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabelEmpName.setText("Pedro Almeida");

        jLabel5.setText("Departamento: ");

        jLabelEmpDepartment.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabelEmpDepartment.setText("Tesouraria");

        jLabel7.setText("Mesa:");

        jLabelEmpDesk.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabelEmpDesk.setText("jLabel6");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelEmpName))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelEmpDepartment))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelEmpDesk)))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEmpDepartment))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEmpDesk)))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 190, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ual/lp/caller/images/UAL_Logo.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 119, 163), 1, true), "Senhas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 119, 163)));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Último criado", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 119, 163)));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(90, 61));

        jLabelLastTicket.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabelLastTicket.setForeground(new java.awt.Color(40, 87, 226));
        jLabelLastTicket.setText("D999");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabelLastTicket)
                .addGap(0, 4, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabelLastTicket))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "A atender", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 119, 163)));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(90, 61));

        jLabelActualTicket.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabelActualTicket.setForeground(new java.awt.Color(40, 87, 226));
        jLabelActualTicket.setText("D999");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabelActualTicket)
                .addGap(0, 4, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabelActualTicket))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 160, 190));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 119, 163), 1, true));

        jButtonRefresh.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ual/lp/caller/images/RefreshButton120x120.png"))); // NOI18N
        jButtonRefresh.setToolTipText("Rechamar");
        jButtonRefresh.setContentAreaFilled(false);
        jButtonRefresh.setFocusCycleRoot(true);
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });

        jButtonCallNext.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCallNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ual/lp/caller/images/Play-icon120x120.png"))); // NOI18N
        jButtonCallNext.setToolTipText("");
        jButtonCallNext.setContentAreaFilled(false);
        jButtonCallNext.setFocusCycleRoot(true);
        jButtonCallNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCallNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jButtonCallNext, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCallNext, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 290, 150));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        try {
            remoteObject.recallTicket(ticket);
        } catch (RemoteException ex) {
            callerLog.error("Existe um problema de comunicação\n com o servidor", ex);
            System.exit(1);
        }

    }//GEN-LAST:event_jButtonRefreshActionPerformed

    public void setLastTicket() {

        System.out.println("Estou a chamar o metodo setLastTicket.");
//        debugLog.info("Estou a chamar o metodo setLastTicket.");

        try {
            ticketLast = remoteObject.getLastTicket(employee);
            jLabelLastTicket.setText(this.ticketLast.getDepartment().getAbbreviation() + String.valueOf(this.ticketLast.getNumberticket()));

        } catch (RemoteException ex) {
            callerLog.error("Erro ao obter o último ticket criado.", ex);
            JOptionPane.showMessageDialog(this, "Existe um problema de comunicação com o servidor.\nContacte o administrador do sistema!");
            System.exit(1);
        } catch (NoTicketsException no) {
            callerLog.info(no);
            jLabelLastTicket.setText("");
        }
    }

    private void jButtonCallNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCallNextActionPerformed

        //Testando o callerMGR para ser se funciona.
        if (jLabelActualTicket.getText().equals("")) {
            try {
                this.ticket = remoteObject.getNextTicket(this.employee);
                jComboBoxColleagues.setSelectedIndex(0);
                jLabelActualTicket.setText(this.ticket.getDepartment().getAbbreviation() + String.valueOf(this.ticket.getNumberticket()));

//             jLabelLastTicket.setText(this.ticketLast.getDepartment().getAbbreviation()+String.valueOf(this.ticketLast.getNumberticket()));
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(this, "Existe um problema de comunicação com o servidor.\nContacte o administrador do sistema!");
                callerLog.error("Existe um problema de comunicação\n com o servidor", ex);
                System.exit(1);
            } catch (NoTicketsException ex) {
                JOptionPane.showMessageDialog(this, "Não existem tickets por atender.");
                jLabelActualTicket.setText("");
                jComboBoxColleagues.setSelectedIndex(0);
            }
        } else {

            try {
                if (jComboBoxColleagues.getSelectedItem() != "") {
//                System.out.println("Transferir para " + jComboBoxColleagues.getSelectedItem());
                    //correr o array dos emp para encontrar o que quero fazer a transf.
                    for (int i = 0; i < this.employees.size(); i++) {
                        if (employees.get(i).getName().equals(jComboBoxColleagues.getSelectedItem())) {
                            this.employees.get(i);
                            System.out.println("Encontrei o gajo na lista.\nIrei transferir para " + employees.get(i).getName());
                            System.out.println(i);
                            this.ticket.setTransferId(this.employees.get(i).getEmpNumber());
                            remoteObject.transferTicket(ticket);
                            break;

                        }
                    }
                    this.ticket = remoteObject.getNextTicket(this.employee);
                    jLabelActualTicket.setText(this.ticket.getDepartment().getAbbreviation() + "" + String.valueOf(this.ticket.getNumberticket()));
                    jComboBoxColleagues.setSelectedIndex(0);
                } else {

                    remoteObject.closeTicket(this.ticket);
                    this.ticket = remoteObject.getNextTicket(this.employee);
                    jLabelActualTicket.setText(this.ticket.getDepartment().getAbbreviation() + "" + String.valueOf(this.ticket.getNumberticket()));
                }
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(this, "Existe um problema de comunicação com o servidor.\nContacte o administrador do sistema!");
                callerLog.error("Existe um problema de comunicação\n com o servidor", ex);
                System.exit(1);
            } catch (NoTicketsException ex) {
                JOptionPane.showMessageDialog(this, "Não existem tickets por atender.");
                callerLog.info("Não existem tickets por atender", ex);
                jLabelActualTicket.setText("");
                jComboBoxColleagues.setSelectedIndex(0);
            }

        }

    }//GEN-LAST:event_jButtonCallNextActionPerformed

    private void jComboBoxColleaguesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxColleaguesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxColleaguesActionPerformed

    /*Antigo método para sair da aplicação, funciona perfeitamente mas foi decidido sair pela cruz.
     int option = JOptionPane.showConfirmDialog(this, "Gostaria realmente de encerrar o caller?", "iSenhas", JOptionPane.YES_NO_OPTION);

     if (option == JOptionPane.YES_OPTION) {
     //            System.out.println("Encerrando o caller");
     try {
     remoteObject.closeTicket(this.ticket);
     //                JOptionPane.showMessageDialog(this, "A aplicação foi encerrada com sucesso.");
     System.exit(0);
     } catch (RemoteException e) {
     System.err.println("O server esta off-line.\n" + e.getMessage());
     callerLog.error("O server esta off-line.", e);
     JOptionPane.showMessageDialog(this, "O servidor esta off-line.\nContacte o administrador do sistema.");
     System.exit(1);
     } catch (NullPointerException e) {
     //            System.err.println("O server esta off-line.\n" + e.getMessage());
     callerLog.error("O server esta off-line.", e);
     //                JOptionPane.showMessageDialog(this, "A aplicação foi encerrada com sucesso.");
     System.exit(0);
     }
     } else {
     JOptionPane.showMessageDialog(this, "Operação anulada!");
     }
     */

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (evt.isPopupTrigger()) {
            jPopupAmin.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_formMouseReleased

    private void jMenuIResetQueuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuIResetQueuActionPerformed
        if (employee.isAdmin() != true) {
            JOptionPane.showMessageDialog(this, "Peça ao administrador do departamento "
                    + "fazer o reset da fila.");
        } else {
            try {
                remoteObject.resetQueue(employee);
            } catch (RemoteException e) {
                System.err.println("Erro ao fazer o reset da fila" + e.getMessage());
            }
            JOptionPane.showMessageDialog(this, "Feito o reset da fila.");
        }


    }//GEN-LAST:event_jMenuIResetQueuActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(CallerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(CallerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(CallerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(CallerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
////                new CallerPanel().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCallNext;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JComboBox jComboBoxColleagues;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelActualTicket;
    private javax.swing.JLabel jLabelEmpDepartment;
    private javax.swing.JLabel jLabelEmpDesk;
    private javax.swing.JLabel jLabelEmpName;
    private javax.swing.JLabel jLabelLastTicket;
    private javax.swing.JMenuItem jMenuIResetQueu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu jPopupAmin;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the remoteObject
     */
    public ServerInf getRemoteObject() {
        return remoteObject;
    }

    /**
     * @param remoteObject the remoteObject to set
     */
    public void setRemoteObject(ServerInf remoteObject) {
        this.remoteObject = remoteObject;
    }

    /**
     * @return the callerInf
     */
    public CallerInf getCallerInf() {
        return callerInf;
    }

    /**
     * @param callerInf the callerInf to set
     */
    public void setCallerInf(CallerInf callerInf) {
        this.callerInf = callerInf;
    }

    /**
     * @return the myIP
     */
    public String getMyIP() {
        return myIP;
    }

    /**
     * @return the serverIP
     */
    public String getServerIP() {
        return serverIP;
    }

}
