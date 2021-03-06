/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachatudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Nguyen Hai Dang
 */
public class ServerFrame extends javax.swing.JFrame {

    private int port;
    private boolean isConnected;
    private DatagramPacket curPacket;
    private DatagramSocket serverSocket;
    private Map<DatagramPacket, String> listClient;
    private DefaultListModel logListModel;

    /**
     * Creates new form ServerFrame
     */
    public ServerFrame() {
        initComponents();
        isConnected = false;
        listClient = new HashMap<>();
        logListModel = new DefaultListModel();
        historyLogList.setModel(logListModel);
    }

    private String receiveData(DatagramSocket client) throws IOException {
        byte[] buff = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        client.receive(packet);
        curPacket = packet;

        String data = new String(packet.getData()).trim();
        if (data.contains("/id")) {
            String tmp[] = data.split("-");
            String idRoom = tmp[1].substring(0, 5);
            addClientToList(packet, idRoom);
            return "/idr-" + idRoom + "-" + tmp[2];
        }

        return data;
    }

    private void sendData(String value, DatagramSocket server, InetAddress clientIP, int clientPort) throws IOException {
        byte[] buff = value.getBytes();
        DatagramPacket packet = new DatagramPacket(buff, buff.length, clientIP, clientPort);
        server.send(packet);
    }

    private void addClientToList(DatagramPacket clientPacket, String idRoom) {
        for (DatagramPacket curClient : listClient.keySet()) {
            if (curClient.getAddress().equals(clientPacket.getAddress())
                    && curClient.getPort() == clientPacket.getPort()) {
                listClient.replace(clientPacket, idRoom);
                return;
            }
        }
        listClient.put(clientPacket, idRoom);
    }

    private void sendDataToOtherSocket(String value, DatagramSocket server) throws IOException {
        String idRoom = "";
        for (DatagramPacket client : listClient.keySet()) {
            if (client.getSocketAddress().equals(this.curPacket.getSocketAddress())
                    && client.getPort() == this.curPacket.getPort()) {
                idRoom = listClient.get(client);
            }
        }

        for (DatagramPacket client : listClient.keySet()) {
            if (!(client.getSocketAddress().equals(this.curPacket.getSocketAddress())
                    && client.getPort() == this.curPacket.getPort()) && listClient.get(client).equals(idRoom)) {
                sendData(value, server, client.getAddress(), client.getPort());
            }
        }
    }

    private void sendDataToAll(String value, DatagramSocket server) throws IOException {
        for (DatagramPacket client : listClient.keySet()) {
            sendData(value, server, client.getAddress(), client.getPort());
        }
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("[0-9.]+");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        messageTextArea = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        historyLogList = new javax.swing.JList<>();
        runButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        portTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        messageTextArea.setColumns(20);
        messageTextArea.setLineWrap(true);
        messageTextArea.setRows(1);
        jScrollPane1.setViewportView(messageTextArea);

        sendButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sendButton.setText("G???i");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        historyLogList.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(historyLogList);

        runButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        runButton.setText("B???t ?????u");
        runButton.setMaximumSize(new java.awt.Dimension(75, 25));
        runButton.setMinimumSize(new java.awt.Dimension(75, 25));
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("M??Y CH???");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("C???ng m???ng");

        portTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(portTextField)
                        .addGap(18, 18, 18)
                        .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(portTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(runButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        if (isConnected) {
            serverSocket.close();
            isConnected = false;
            runButton.setText("B???t ?????u");
            logListModel.addElement("~ Server ???? d???ng !");
            return;
        }

        String portText = portTextField.getText().trim();
        if (!isNumeric(portText)) {
            JOptionPane.showMessageDialog(this, "Vui l??ng nh???p c???ng m???ng h???p l??? (k?? t??? s???)");
            return;
        }

        try {
            port = Integer.valueOf(portText);
            serverSocket = new DatagramSocket(port);

            isConnected = true;
            runButton.setText("D???ng");
            logListModel.addElement("~ Server ??ang ch???y...");

            Thread recvThread = new Thread(() -> {
                while (isConnected) {
                    try {
                        String message = receiveData(serverSocket);

                        if (message.equals("/conrq")) {
                            logListModel.addElement("> Socket #" + curPacket.getPort() + " ???? k???t n???i v???i server !");
                            sendData("/accepted", serverSocket, curPacket.getAddress(), curPacket.getPort());
                            continue;
                        }

                        if (message.contains("/idr")) {
                            String tmp[] = message.split("-");
                            logListModel.addElement("> G???i m?? ph??ng " + tmp[1] + " cho Socket #" + curPacket.getPort());
                            sendData(tmp[1], serverSocket, curPacket.getAddress(), curPacket.getPort());
                            sendDataToOtherSocket("-----> " + tmp[2] + " ???? tham gia ph??ng chat <-----", serverSocket);
                            continue;
                        }

                        if (message.contains("/ext")) {
                            logListModel.addElement("> Socket #"+curPacket.getPort()+" ???? ng???t k???t n???i !");
                            sendDataToOtherSocket("-----> " + message.split("-")[1] + " ???? r???i ph??ng chat <-----", serverSocket);
                            for (DatagramPacket client : listClient.keySet()) {
                                if (client.getSocketAddress().equals(this.curPacket.getSocketAddress())
                                        && client.getPort() == this.curPacket.getPort()) {
                                    listClient.remove(client);
                                    break;
                                }
                            }
                            continue;
                        }

                        logListModel.addElement(message);
                        sendDataToOtherSocket(message, serverSocket);
                    } catch (Exception ex) {
                        System.out.println("Receive error: " + ex);
                    }
                }
            });
            recvThread.start();
        } catch (Exception ex) {
            System.out.println("Start error: " + ex);
        }
    }//GEN-LAST:event_runButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        String message = messageTextArea.getText();

        if (message.isEmpty()) {
            return;
        }

        messageTextArea.setText("");
        try {
            message = "Server: " + message;
            logListModel.addElement(message);
            sendDataToAll(message, serverSocket);
        } catch (Exception ex) {
            System.out.println("Send error: " + ex);
        }
    }//GEN-LAST:event_sendButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ServerFrame mainFrame = new ServerFrame();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> historyLogList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea messageTextArea;
    private javax.swing.JTextField portTextField;
    private javax.swing.JButton runButton;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
