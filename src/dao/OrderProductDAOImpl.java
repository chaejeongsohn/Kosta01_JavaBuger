package dao;

import dto.OrderOption;
import dto.OrderProduct;
import dto.Payment;
import utils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class OrderProductDAOImpl implements OrderProductDAO {
	private Properties proFile = DbUtils.getProFile();
	//OrderOptionService orderOptionService = new OrderOptionService();
	OrderOptionDAOImpl orderoptionimpl = new OrderOptionDAOImpl();
	ProductDAOImpl productDAO = new ProductDAOImpl();

    @Override
    public int[] insertOrderProduct(Connection con , List<OrderProduct> orderProductList) throws SQLException {
        PreparedStatement ps = null;
        String sql = proFile.getProperty("orderproduct.insert");
    	//int result []=null;
    	
    	try {
            //System.out.println("오더프로덕트");
            ps = con.prepareStatement(sql);
            for (OrderProduct order : orderProductList) {
                ps.setInt(1, order.getProductNumber());
                ps.setInt(2, order.getOrderProductAmount());
                if(ps.executeUpdate() == 0){
                    con.rollback();
                    throw new SQLException("[주문 실패] 옵션 주문에 오류가 있습니다. 주문하지 못 했습니다.");
                }
                List<OrderOption> orderop = order.getOrderoptionlist();
                if (orderop != null) {
                    int[] re = orderoptionimpl.insertOrderOption(con, order.getOrderoptionlist());
                    for (int j : re) {
                        if (j != 1) {
                            con.rollback();
                            throw new SQLException("[주문 실패] 옵션 주문에 오류가 있습니다. 주문하지 못 했습니다.");
                        }
                    }
                }
                ps.clearParameters();
            }
        } finally {
            DbUtils.close(null, ps, null);
        }
        return new int[]{1};
    }
}
/*
    	try {
    		ps =con.prepareStatement(sql);
    		for(OrderProduct order : orderProductList) {
    			ps.setInt(1, order.getProductNumber());
    			ps.setInt(2, order.getOrderProductAmount());
    			ps.addBatch();
    			ps.clearParameters();
    			
    			result =ps.executeBatch();
    			for(int i : result) {
    				if(i!=1) {
    					con.rollback();
    					
    				}else {
    					List<OrderOption> orderop= order.getOrderoptionlist();
    	    			if(orderop!=null) {
    	    				int re[] = orderoptionimpl.insertOrderOption(con,order.getOrderoptionlist());
    	    				for(int j :re) {
    	    					if(j!=1) {
    	    						con.rollback();
    	    						throw new SQLException("[주문 실패] 옵션 주문에 오류가 있습니다. 주문하지 못 했습니다.");
    	    						
    	    					}
    	    				}
    	    			}
    	    			
    				}
    			}

    		}
    		
    	}finally {
    		DbUtils.close(null, ps, null);
    	}
    	return result;
    }
}*/
    	