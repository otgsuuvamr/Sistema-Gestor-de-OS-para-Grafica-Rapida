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
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Tela de Ordens de Serviço.
 *
 * @author Gustavo Marichi Rosendo
 * @version 1.0.1
 */
public class OSScreen extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private String pagamento;

    /**
     * Creates new form OSScreen
     */
    public OSScreen() {
        initComponents();
        conexao = ConnectModule.conector();
    }

    private void searchCli() {
        String sql = "select idcli as ID, nomecli as NOME, phonecli as FONE, emailcli as EMAIL from clientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliPesquisa.getText() + "%");
            rs = pst.executeQuery();
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }

    private void setFields() {
        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
    }

    private void emitirOS() {
        String sql = "insert into osinfo (idcli, produto, descricao, funcionario, status, quantidade, valorttl, pagamento) values(?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliId.getText());
            pst.setString(2, txtProduct.getText());
            pst.setString(3, txtDescription.getText());
            pst.setString(4, txtFunc.getText());
            pst.setString(5, cboSts.getSelectedItem().toString());
            pst.setString(6, txtQtd.getText());
            pst.setString(7, txtValorTtl.getText().replace(",", ".")); // Substituição de caractere na string de , para . .
            pst.setString(8, pagamento);

            if ((txtCliId.getText().isEmpty()) || (txtProduct.getText().isEmpty()) || (txtFunc.getText().isEmpty()) || cboSts.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios para emitir a OS");
            } else {
                int added = pst.executeUpdate();
                if (added > 0) {
                    JOptionPane.showMessageDialog(this, "OS emitida com sucesso!");
                    osRecover();
                    btnCreateOS.setEnabled(false);
                    btnSeacrhOS.setEnabled(false);
                    btnPrintOS.setEnabled(true);
                    btnEditOS.setEnabled(false);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void searchOS() {
        String numOs = JOptionPane.showInputDialog(this, "Por qual OS está buscando?");
        String sql = "select os, date_format(data_os, '%d/%m/%Y - %H:%i'), idcli, produto, descricao, funcionario, status, quantidade, valorttl, pagamento from osinfo where os =" + numOs;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOs.setText(rs.getString(1));
                txtData.setText(rs.getString(2));
                txtCliId.setText(rs.getString(3));
                txtProduct.setText(rs.getString(4));
                txtDescription.setText(rs.getString(5));
                txtFunc.setText(rs.getString(6));
                cboSts.setSelectedItem(rs.getString(7));
                txtQtd.setText(rs.getString(8));
                txtValorTtl.setText(rs.getString(9));
                String rbtPg = rs.getString(10);
                if (rbtPg.equals("Dinheiro")) {
                    rbtDinheiro.setSelected(true);
                    pagamento = "Dinheiro";
                } else if (rbtPg.equals("Débito")) {
                    rbtDebito.setSelected(true);
                    pagamento = "Débito";
                } else if (rbtPg.equals("Crédito")) {
                    rbtCredito.setSelected(true);
                    pagamento = "Crédito";
                } else {
                    rbtPix.setSelected(true);
                    pagamento = "Pix";
                }
                btnCreateOS.setEnabled(false);
                txtCliPesquisa.setEnabled(false);
                tblClientes.setVisible(false);
                btnSeacrhOS.setEnabled(false);
                // Ativando demais botões
                btnEditOS.setEnabled(true);
                btnPrintOS.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(this, "OS inexistente.");
            }
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(this, "OS inválida");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(this, e2);
        }
    }

    private void updateOS() {
        String sql = "update osinfo set produto = ?, descricao = ?, funcionario = ?, status = ?, quantidade = ?, valorttl = ?, pagamento = ? where os = ?;";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtProduct.getText());
            pst.setString(2, txtDescription.getText());
            pst.setString(3, txtFunc.getText());
            pst.setString(4, cboSts.getSelectedItem().toString());
            pst.setString(5, txtQtd.getText());
            pst.setString(6, txtValorTtl.getText().replace(",", ".")); // Substituição de caractere na string de , para . .
            pst.setString(7, pagamento);
            pst.setString(8, txtOs.getText());

            if ((txtCliId.getText().isEmpty()) || (txtProduct.getText().isEmpty()) || (txtFunc.getText().isEmpty()) || cboSts.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios para emitir a OS");
            } else {
                int added = pst.executeUpdate();
                if (added > 0) {
                    JOptionPane.showMessageDialog(this, "OS alterada com sucesso!");
                    cleanEnable();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void printOs() {
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja imprimir esta OS?", "Atençãoa!", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                HashMap filtro = new HashMap();
                filtro.put("os", Integer.parseInt(txtOs.getText()));
                JasperPrint print = JasperFillManager.fillReport("C:\\Users\\Gustavo\\Downloads\\Reports\\OS.jasper", filtro, conexao);
                JasperViewer.viewReport(print, false);
                cleanEnable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }

    private void osRecover() {
        String sql = "select max(os) from osinfo;";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOs.setText(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void cleanEnable() {
        txtOs.setText(null);
        txtData.setText(null);
        txtCliId.setText(null);
        txtProduct.setText(null);
        txtDescription.setText(null);
        txtFunc.setText(null);
        txtQtd.setText(null);
        txtValorTtl.setText("0,00");
        txtCliPesquisa.setText(null);
        cboSts.setSelectedItem(null);
        ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
        btnCreateOS.setEnabled(true);
        btnSeacrhOS.setEnabled(true);
        txtCliPesquisa.setEnabled(true);
        tblClientes.setVisible(true);
        btnPrintOS.setEnabled(false);
        btnEditOS.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cboSts = new javax.swing.JComboBox<>();
        txtData = new javax.swing.JTextField();
        txtOs = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtCliPesquisa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtProduct = new javax.swing.JTextField();
        txtDescription = new javax.swing.JTextField();
        txtFunc = new javax.swing.JTextField();
        txtQtd = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtValorTtl = new javax.swing.JTextField();
        btnCreateOS = new javax.swing.JButton();
        btnSeacrhOS = new javax.swing.JButton();
        btnEditOS = new javax.swing.JButton();
        btnPrintOS = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        rbtDebito = new javax.swing.JRadioButton();
        rbtCredito = new javax.swing.JRadioButton();
        rbtPix = new javax.swing.JRadioButton();
        rbtDinheiro = new javax.swing.JRadioButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ordem de Serviço");
        setPreferredSize(new java.awt.Dimension(898, 660));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("* Estado do Pedido:");

        jLabel4.setText("Data:");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/StartBlackLogo.png"))); // NOI18N

        jLabel1.setText("Nº :");

        cboSts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Efetuado", "Esperando Pagamento", "Produzindo", "Terminado", "Esperando Retirada", "Finalizado" }));
        cboSts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboStsActionPerformed(evt);
            }
        });

        txtData.setEditable(false);
        txtData.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txtData.setMinimumSize(new java.awt.Dimension(15, 24));
        txtData.setPreferredSize(new java.awt.Dimension(15, 24));

        txtOs.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cboSts, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtData, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(txtOs))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel3))
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboSts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtCliPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisaKeyReleased(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/CustumerSearch.png"))); // NOI18N

        jLabel6.setText("* ID:");

        txtCliId.setEditable(false);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Fone", "E-mail"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                        .addGap(16, 16, 16))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCliPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCliPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("* Produto:");

        jLabel8.setText("* Descrição:");

        jLabel9.setText("* Funcionário:");

        jLabel10.setText("* Quantidade:");

        txtDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescriptionActionPerformed(evt);
            }
        });

        txtQtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtdActionPerformed(evt);
            }
        });

        jLabel11.setText("* Valor Total:");

        txtValorTtl.setText("0");
        txtValorTtl.setToolTipText("");

        btnCreateOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/OsAdd.png"))); // NOI18N
        btnCreateOS.setToolTipText("Criar OS");
        btnCreateOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreateOS.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCreateOS.setPreferredSize(new java.awt.Dimension(76, 72));
        btnCreateOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateOSActionPerformed(evt);
            }
        });

        btnSeacrhOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/OsSearch.png"))); // NOI18N
        btnSeacrhOS.setToolTipText("Procurar OS");
        btnSeacrhOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSeacrhOS.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSeacrhOS.setPreferredSize(new java.awt.Dimension(76, 72));
        btnSeacrhOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeacrhOSActionPerformed(evt);
            }
        });

        btnEditOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/OsEdit.png"))); // NOI18N
        btnEditOS.setToolTipText("Editar OS");
        btnEditOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditOS.setEnabled(false);
        btnEditOS.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditOS.setPreferredSize(new java.awt.Dimension(76, 72));
        btnEditOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditOSActionPerformed(evt);
            }
        });

        btnPrintOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/scos/icons/OsPrint.png"))); // NOI18N
        btnPrintOS.setToolTipText("Imprimir OS");
        btnPrintOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrintOS.setEnabled(false);
        btnPrintOS.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrintOS.setMaximumSize(new java.awt.Dimension(67, 63));
        btnPrintOS.setMinimumSize(new java.awt.Dimension(67, 63));
        btnPrintOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintOSActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Método de Pagamento:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        buttonGroup1.add(rbtDebito);
        rbtDebito.setText("Débito");
        rbtDebito.setPreferredSize(new java.awt.Dimension(110, 16));
        rbtDebito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtDebitoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtCredito);
        rbtCredito.setText("Crédito");
        rbtCredito.setPreferredSize(new java.awt.Dimension(110, 16));
        rbtCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtCreditoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtPix);
        rbtPix.setText("PIX");
        rbtPix.setPreferredSize(new java.awt.Dimension(110, 16));
        rbtPix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtPixActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtDinheiro);
        rbtDinheiro.setText("Dinheiro");
        rbtDinheiro.setPreferredSize(new java.awt.Dimension(110, 16));
        rbtDinheiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtDinheiroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rbtDebito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rbtCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rbtPix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtDebito, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtPix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(159, 159, 159)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(txtValorTtl))
                            .addComponent(txtDescription)
                            .addComponent(txtFunc))))
                .addGap(182, 182, 182))
            .addGroup(layout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCreateOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(btnSeacrhOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(btnEditOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(btnPrintOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtFunc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtValorTtl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCreateOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeacrhOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrintOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78))
        );

        setBounds(0, 0, 898, 660);
    }// </editor-fold>//GEN-END:initComponents

    private void cboStsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboStsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboStsActionPerformed

    private void btnCreateOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateOSActionPerformed
        // TODO add your handling code here:
        emitirOS();
    }//GEN-LAST:event_btnCreateOSActionPerformed

    private void rbtDebitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtDebitoActionPerformed
        // TODO add your handling code here:
        pagamento = "Débito";
    }//GEN-LAST:event_rbtDebitoActionPerformed

    private void txtCliPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisaKeyReleased
        // TODO add your handling code here:
        searchCli();
    }//GEN-LAST:event_txtCliPesquisaKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // TODO add your handling code here:
        setFields();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void rbtDinheiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtDinheiroActionPerformed
        // TODO add your handling code here:
        pagamento = "Dinehiro";
    }//GEN-LAST:event_rbtDinheiroActionPerformed

    private void rbtCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtCreditoActionPerformed
        // TODO add your handling code here:
        pagamento = "Crédito";
    }//GEN-LAST:event_rbtCreditoActionPerformed

    private void rbtPixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtPixActionPerformed
        // TODO add your handling code here:
        pagamento = "Pix";
    }//GEN-LAST:event_rbtPixActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        rbtDinheiro.setSelected(true);
        pagamento = "Dinheiro";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnSeacrhOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeacrhOSActionPerformed
        // TODO add your handling code here:
        searchOS();
    }//GEN-LAST:event_btnSeacrhOSActionPerformed

    private void btnEditOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditOSActionPerformed
        // TODO add your handling code here:
        updateOS();
    }//GEN-LAST:event_btnEditOSActionPerformed

    private void txtQtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtdActionPerformed

    private void txtDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescriptionActionPerformed

    private void btnPrintOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintOSActionPerformed
        // TODO add your handling code here:
        printOs();
    }//GEN-LAST:event_btnPrintOSActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateOS;
    private javax.swing.JButton btnEditOS;
    private javax.swing.JButton btnPrintOS;
    private javax.swing.JButton btnSeacrhOS;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboSts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public static javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtCredito;
    private javax.swing.JRadioButton rbtDebito;
    private javax.swing.JRadioButton rbtDinheiro;
    private javax.swing.JRadioButton rbtPix;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliPesquisa;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtFunc;
    private javax.swing.JTextField txtOs;
    private javax.swing.JTextField txtProduct;
    private javax.swing.JTextField txtQtd;
    private javax.swing.JTextField txtValorTtl;
    // End of variables declaration//GEN-END:variables
}
