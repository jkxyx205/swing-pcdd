package com.rick.ui;


import com.rick.Constants;
import com.rick.model.Param;
import com.rick.model.Result;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DataPanel extends JScrollPane {
    private Param param;

    private List<Result> list;

    private JTable tb = new JTable() {
        public boolean isCellEditable(int row,int col){
            return  false;
        }
    };

    public DataPanel( Param param, List<Result> list) {
        this.param = param;
        this.list = list;
        refresh(param,list);
    }
    private String[][] getTableData() {
        int NUM = param.getR();
        String[][] data = new String[NUM][8];

        for (int i = 0; i < NUM; i++) {
            Result r = list.get(i);
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = r.getId();
            data[i][2] = r.getDate();
            data[i][3] = r.getQuestion();
            data[i][4] = String.valueOf(r.getSingleOrDouble());
            data[i][5] = String.valueOf(r.getSingleOrDouble());
            data[i][6] = String.valueOf(r.getSmallOrBig());
            data[i][7] = String.valueOf(r.getSmallOrBig());
         }
        return data;
    }

    public void refresh(Param param,List<Result> list) {
        this.param = param;
        this.list = list;
        String[] tableHeads = { "序号","期号","开奖时间","开奖结果","单","双","大","小"};
        DefaultTableModel model = new DefaultTableModel(getTableData(),tableHeads);
        tb.setModel(model);
        tb.setBorder(BorderFactory.createEtchedBorder());
        tb.setRowHeight(20);

        //设置列宽
        tb.getColumnModel().getColumn(0).setPreferredWidth(10);
        tb.getColumnModel().getColumn(4).setPreferredWidth(10);
        tb.getColumnModel().getColumn(5).setPreferredWidth(10);
        tb.getColumnModel().getColumn(6).setPreferredWidth(10);
        tb.getColumnModel().getColumn(7).setPreferredWidth(10);

        DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER); //居中显示
        tb.setDefaultRenderer(Object.class,   r);

        tb.getColumnModel().getColumn(3).setCellRenderer(new ResultCellRenderrer());
        tb.getColumnModel().getColumn(4).setCellRenderer(new BgColorCellRenderrer());
        tb.getColumnModel().getColumn(5).setCellRenderer(new BgColorCellRenderrer());
        tb.getColumnModel().getColumn(6).setCellRenderer(new BgColorCellRenderrer());
        tb.getColumnModel().getColumn(7).setCellRenderer(new BgColorCellRenderrer());

//        this.add(tb);
        this.setViewportView(tb);
    }
}

class BgColorCellRenderrer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row,
                                                   int column) {
        BgColorCellRenderrer comp = (BgColorCellRenderrer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        comp.setText("");
        String v = (String) value;
        comp.setBackground(Color.white);
        if (column == 4 && "0".equals(v))
            comp.setBackground(Constants.COLOR_TITLE_SINGLE);
        else  if (column == 5 && "1".equals(v))
            comp.setBackground(Constants.COLOR_TITLE_DOUBLE);
        else  if (column == 6 && "1".equals(v))
            comp.setBackground(Constants.COLOR_TITLE_BIG);
        else  if (column == 7 && "0".equals(v))
            comp.setBackground(Constants.COLOR_TITLE_SMALL);
        return comp;
    }
}

class ResultCellRenderrer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row,
                                                   int column){
        String val = (String)value;
        String s[] = val.split("=");
        JLabel result = new JLabel("<html><div style='font-size:9px;font-weight:normal; color:#333333;'>"+s[0]+" = <span style='color:red;'>"+s[1]+"</span></div></html>");
        result.setHorizontalAlignment(JLabel.CENTER);
        return result;
    }
}