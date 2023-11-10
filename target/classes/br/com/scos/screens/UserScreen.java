/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.scos.screens;

/*
    @author Gustavo
 */
import java.sql.*;
import br.com.scos.dal.ConnectModule;
import javax.swing.JOptionPane;

public class UserScreen extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form UserScreen
     */
    public UserScreen() {
        initComponents();
        conexao = ConnectModule.conector();
    }

    // Método Consultor de usuários.
    private void readUser() {
        String sql = "select * from userinfo where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtID.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUsername.setText(rs.getString(2));
                txtLogin.setText(rs.getString(3));
                txtPswrd.setText(rs.getString(4));
                // Esta linha se refere ao ComboBox (caixa de seleção entre Admin e User)
                cboxHierarchy.setSelectedItem(rs.getString(5));
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado ou inexistente, verifique as credenciais.");
                // Limpando os campos pré-preenchidos
                txtUsername.setText(null);
                txtLogin.setText(null);
                txtPswrd.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    // Método Criador de usuários.
    private void createUser() {
        String sql = "insert into userinfo (iduser, usr, login, pswrd, perfil) values(?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtID.getText());
            pst.setString(2, txtUsername.getText());
            pst.setString(3, txtLogin.getText());
            pst.setString(4, txtPswrd.getText());
            pst.setString(5, cboxHierarchy.getSelectedItem().toString());
            
            // Valida os campos obrigatórios.
            if ((txtID.getText().isEmpty())||(txtUsername.getText().isEmpty())
            ||(txtLogin.getText().isEmpty())||(txtPswrd.getText().isEmpty())) {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!");
            } else {

                // Insere na tabela "userinfo" os dados capturados do formulário "Usuários".
                // Confirma com uma mensagem a inserção de dados.
                int added = pst.executeUpdate();
                if (added > 0) {
                    JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                    txtID.setText(null);
                    txtUsername.setText(null);
                    txtLogin.setText(null);
                    txtPswrd.setText(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }
    
    // Método Editor de usuários.
    private void updateUser() {
        String sql = "update userinfo set usr=?, login=?, pswrd=?, perfil=? where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsername.getText());
            pst.setString(2, txtLogin.getText());
            pst.setString(3, txtPswrd.getText());
            pst.setString(4, cboxHierarchy.getSelectedItem().toString());
            pst.setString(5, txtID.getText());
            // Valida os campos obrigatórios.
            if ((txtID.getText().isEmpty())||(txtUsername.getText().isEmpty())
            ||(txtLogin.getText().isEmpty())||(txtPswrd.getText().isEmpty())) {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!");
            } else {

                // Insere na tabela "userinfo" os dados capturados do formulário "Usuários".
                // Confirma com uma mensagem a atualização de dados.
                int added = pst.executeUpdate();
                if (added > 0) {
                    JOptionPane.showMessageDialog(this, "Dados do usuário atualizado com sucesso!");
                    txtID.setText(null);
                    txtUsername.setText(null);
                    txtLogin.setText(null);
                    txtPswrd.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    // Método Removedor de usuários.
    private void deleteUser() {
        // Confirmação da remoção de um usuário.
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este usuário?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "delete from userinfo where iduser=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtID.getText());
                
                // Remove da tabela "userinfo" os dados do usuário com o "iduser" correspondente.
                // Confirma a exclusão do usuário.
                int deleted = pst.executeUpdate();
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(this, "Usuário deletado com sucesso!");
                    txtID.setText(null);
                    txtUsername.setText(null);
                    txtLogin.setText(null);
                    txtPswrd.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtLogin = new javax.swing.JTextField();
        txtPswrd = new javax.swing.JTextField();
        cboxHierarchy = new javax.swing.JComboBox<>();
        btnUserRead = new javax.swing.JButton();
        btnUserUpdate = new javax.swing.JButton();
        btnUserDelete = new javax.swing.JButton();
        btnUserCreate = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setPreferredSize(new java.awt.Dimension(898, 660));

        jLabel1.setText("* Nome:");

        jLabel2.setText("* Login:");

        jLabel3.setText("* Senha:");

        jLabel4.setText("* Hierarquia:");

        cboxHierarchy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));

        btnUserRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/UserSearch.png"))); // NOI18N
        btnUserRead.setToolTipText("Procurar Usuário");
        btnUserRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserReadActionPerformed(evt);
            }
        });

        btnUserUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/UserEdit.png"))); // NOI18N
        btnUserUpdate.setToolTipText("Editar Usuário");
        btnUserUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserUpdateActionPerformed(evt);
            }
        });

        btnUserDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/UserRemove.png"))); // NOI18N
        btnUserDelete.setToolTipText("Deletar Usuário");
        btnUserDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserDeleteActionPerformed(evt);
            }
        });

        btnUserCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/UserAdd.png"))); // NOI18N
        btnUserCreate.setToolTipText("Criar Usuário");
        btnUserCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserCreateActionPerformed(evt);
            }
        });

        jLabel5.setText("* ID:");

        jLabel6.setText("* Campos Obrigatórios.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1)
                                        .addComponent(cboxHierarchy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtPswrd, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtLogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 227, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(218, Short.MAX_VALUE)
                        .addComponent(btnUserCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(btnUserRead, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(btnUserUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(btnUserDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(219, 219, 219))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPswrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboxHierarchy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUserRead, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83))
        );

        setBounds(0, 0, 898, 660);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUserReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserReadActionPerformed
        /* Chama o método Consultar (pesquisa no banco de dados/readUser)
         */
        readUser();
    }//GEN-LAST:event_btnUserReadActionPerformed

    private void btnUserCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserCreateActionPerformed
        /* Chama o método Criar (insere no banco de dados/createUser)
         */
        createUser();
    }//GEN-LAST:event_btnUserCreateActionPerformed

    private void btnUserUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserUpdateActionPerformed
        /* Chama o método Alterar (atualiza no banco de dados/updateUser)
         */ 
        updateUser();
    }//GEN-LAST:event_btnUserUpdateActionPerformed

    private void btnUserDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserDeleteActionPerformed
        /* Chama o método Deletar (remove do banco de dados/deleteUser)
         */
        deleteUser();
    }//GEN-LAST:event_btnUserDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUserCreate;
    private javax.swing.JButton btnUserDelete;
    private javax.swing.JButton btnUserRead;
    private javax.swing.JButton btnUserUpdate;
    private javax.swing.JComboBox<String> cboxHierarchy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtPswrd;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
