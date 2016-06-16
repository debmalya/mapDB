package form;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import Controller.StockController;
import model.StockSymbol;
import service.StockService;

public class StockForm extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 41273037243522633L;

	private StockService stockService;

	private StockController stockController;

	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable jTable1;

	/**
	 * Creates new form InvoiceForm
	 */
	public StockForm(StockService invoiceService, StockController invoiceController) {
		this.stockService = invoiceService;
		this.stockController = invoiceController;

		invoiceController.registerChangeListener(this);

//		initComponents();
		updateModel();
		jTable1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable) e.getSource();
				int row = target.getSelectedRow();
				int column = target.getSelectedColumn();

				if (column != 3) {
					return;
				}
//				Invoice invoice = invoiceMap.get(row);
//				invoiceController.markPaid(invoice);
			}
		});
	}

	final public void updateModel() {
		TableModel model = jTable1.getModel();

		List<StockSymbol> stockSymbols = stockService.getStockSymbols();
//		invoiceMap = new HashMap<>();
		for (int i = 0; i < stockSymbols.size(); i++) {
//			Invoice invoice = invoices.get(i);
//			invoiceMap.put(i, invoice);

			
		}
	}

}
