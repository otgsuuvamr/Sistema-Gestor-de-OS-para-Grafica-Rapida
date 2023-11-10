/*
 * The MIT License
 *
 * Copyright 2023 Rosendoo.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.com.scos.screens;

import java.sql.*;
import br.com.scos.dal.ConnectModule;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import br.com.scos.screens.DbUtils;

/**
 * Tela de Clientes.
 *
 * @author Gustavo
 * @version 1.0.1
 */
public class ClientScreen extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form ClientScreen
     */
    public ClientScreen() {
        initComponents();
        conexao = ConnectModule.conector();
    }

    private void createCli() {
        String sql = "insert into clientes (nomecli, phonecli, emailcli) values(?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCustumerName.getText());
            pst.setString(2, txtCustumerFone.getText());
            pst.setString(3, txtCustumerEmail.getText());

            if ((txtCustumerName.getText().isEmpty()) || (txtCustumerFone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!");
            } else {

                int added = pst.executeUpdate();
                if (added > 0) {
                    JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                    clear();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }

    private void searchCli() {
        String sql = "select idcli as ID, nomecli as NOME, phonecli as FONE, emailcli as EMAIL from clientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCustumerSearch.getText() + "%");
            rs = pst.executeQuery();
            tblCustumers.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void selectFields() {
        int select = tblCustumers.getSelectedRow();
        txtCustumerId.setText(tblCustumers.getModel().getValueAt(select, 0).toString());
        txtCustumerName.setText(tblCustumers.getModel().getValueAt(select, 1).toString());
        txtCustumerFone.setText(tblCustumers.getModel().getValueAt(select, 2).toString());
        txtCustumerEmail.setText(tblCustumers.getModel().getValueAt(select, 3).toString());
        btnCreateCustumer.setEnabled(false);
    }

    private void updateCli() {
        String sql = "update clientes set nomecli=?, phonecli=?, emailcli=? where idcli=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCustumerName.getText());
            pst.setString(2, txtCustumerFone.getText());
            pst.setString(3, txtCustumerEmail.getText());
            pst.setString(4, txtCustumerId.getText());
            if ((txtCustumerName.getText().isEmpty()) || (txtCustumerFone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!");
            } else {

                int added = pst.executeUpdate();
                if (added > 0) {
                    JOptionPane.showMessageDialog(this, "Dados do cliente atualizado com sucesso!");
                    clear();
                    btnCreateCustumer.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void deleteCli() {
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este cliente?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "delete from clientes where idcli=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCustumerId.getText());

                int deleted = pst.executeUpdate();
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(this, "Cliente deletado com sucesso!");
                    clear();
                    btnCreateCustumer.setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }

    private void clear() {
        txtCustumerId.setText(null);
        txtCustumerName.setText(null);
        txtCustumerFone.setText(null);
        txtCustumerEmail.setText(null);
        txtCustumerSearch.setText(null);
        ((DefaultTableModel) tblCustumers.getModel()).setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCustumerFone = new javax.swing.JTextField();
        txtCustumerName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCustumerSearch = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCustumers = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtCustumerEmail = new javax.swing.JTextField();
        btnCreateCustumer = new javax.swing.JButton();
        btnEditCusutmer = new javax.swing.JButton();
        btnDeleteCustumer = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtCustumerId = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(898, 660));

        jLabel1.setText("* Nome: ");

        jLabel2.setText("* Campos Obrigatórios.");

        jLabel3.setText("* Telefone:");

        txtCustumerFone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustumerFoneActionPerformed(evt);
            }
        });

        txtCustumerSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustumerSearchActionPerformed(evt);
            }
        });
        txtCustumerSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCustumerSearchKeyReleased(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/CustumerSearch.png"))); // NOI18N

        tblCustumers = new javax.swing.JTable() {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblCustumers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "NOME", "TELEFONE", "E-MAIL"
            }
        ));
        tblCustumers.setFocusable(false);
        tblCustumers.getTableHeader().setResizingAllowed(false);
        tblCustumers.getTableHeader().setReorderingAllowed(false);
        tblCustumers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCustumersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCustumers);

        jLabel7.setText("E-mail:");

        txtCustumerEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustumerEmailActionPerformed(evt);
            }
        });

        btnCreateCustumer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/UserAdd.png"))); // NOI18N
        btnCreateCustumer.setToolTipText("Cadastrar Cliente");
        btnCreateCustumer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateCustumerActionPerformed(evt);
            }
        });

        btnEditCusutmer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/UserEdit.png"))); // NOI18N
        btnEditCusutmer.setToolTipText("Alterar Cliente");
        btnEditCusutmer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCusutmerActionPerformed(evt);
            }
        });

        btnDeleteCustumer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/UserRemove.png"))); // NOI18N
        btnDeleteCustumer.setToolTipText("Excluir Cliente");
        btnDeleteCustumer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCustumerActionPerformed(evt);
            }
        });

        jLabel9.setText(" ID:");

        txtCustumerId.setEnabled(false);
        txtCustumerId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustumerIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCustumerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(50, 50, 50))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(365, 365, 365)
                        .addComponent(jLabel4)
                        .addGap(1016, 1016, 1016)
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCreateCustumer)
                                .addGap(75, 75, 75)
                                .addComponent(btnEditCusutmer)
                                .addGap(75, 75, 75)
                                .addComponent(btnDeleteCustumer))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCustumerId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCustumerName)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtCustumerFone, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtCustumerEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 883, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(221, 221, 221)
                    .addComponent(jLabel6)
                    .addContainerGap(1172, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCustumerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addComponent(jLabel2))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCustumerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCustumerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCustumerFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCustumerEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeleteCustumer)
                    .addComponent(btnCreateCustumer)
                    .addComponent(btnEditCusutmer))
                .addGap(33, 33, 33))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(341, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGap(297, 297, 297)))
        );

        setBounds(0, 0, 898, 660);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCustumerSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustumerSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustumerSearchActionPerformed

    private void btnCreateCustumerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateCustumerActionPerformed
        // TODO add your handling code here:
        createCli();
    }//GEN-LAST:event_btnCreateCustumerActionPerformed

    private void btnEditCusutmerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCusutmerActionPerformed
        // TODO add your handling code here:
        updateCli();
    }//GEN-LAST:event_btnEditCusutmerActionPerformed

    private void btnDeleteCustumerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCustumerActionPerformed
        // TODO add your handling code here:
        deleteCli();
    }//GEN-LAST:event_btnDeleteCustumerActionPerformed

    private void txtCustumerSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustumerSearchKeyReleased
        // TODO add your handling code here:
        searchCli();
    }//GEN-LAST:event_txtCustumerSearchKeyReleased

    private void tblCustumersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustumersMouseClicked
        // TODO add your handling code here:
        selectFields();
    }//GEN-LAST:event_tblCustumersMouseClicked

    private void txtCustumerIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustumerIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustumerIdActionPerformed

    private void txtCustumerEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustumerEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustumerEmailActionPerformed

    private void txtCustumerFoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustumerFoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustumerFoneActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateCustumer;
    private javax.swing.JButton btnDeleteCustumer;
    private javax.swing.JButton btnEditCusutmer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCustumers;
    private javax.swing.JTextField txtCustumerEmail;
    private javax.swing.JTextField txtCustumerFone;
    private javax.swing.JTextField txtCustumerId;
    private javax.swing.JTextField txtCustumerName;
    private javax.swing.JTextField txtCustumerSearch;
    // End of variables declaration//GEN-END:variables
}
