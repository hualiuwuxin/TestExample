package com.lomi.demo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.lomi.demo.equip.DAP;
import com.lomi.demo.equip.Model;
import com.lomi.demo.equip.impl.HX;
import com.lomi.demo.equip.impl.WJ;
import com.lomi.demo.equip.impl.XZ;
import com.lomi.demo.equip.impl.ZS;

public class Main {

	public static void main(String[] args) {
		int zjsm = 4000;
		int zjgj = 150;
		
		int dffy = 500;

		List<Model> list = new ArrayList<>();
		list.add( new XZ() );
		//list.add( new YX() );
		list.add( new WJ() );
		//list.add( new ZS() );
		list.add( new HX() );
		//list.add( new DD() );
		
		//list.add( new PX() );
		
		DAP dap = new DAP();
		for(Model item:list) {
			dap.setGJL( item.getGJL() + dap.getGJL()  );
			dap.setGJSD( item.getGJSD() + dap.getGJSD()  );
			
			dap.setBJ( item.getBJ() + dap.getBJ()  );
			dap.setBS( item.getBS() + dap.getBS()  );
			
			dap.setPJ( item.getPJ() + dap.getPJ()  );
			
			dap.setXX( item.getXX() + dap.getXX()  );
			
			dap.setSM( item.getSM() + dap.getSM()  );
			dap.setHX( item.getHX() + dap.getHX()  );
			dap.setDD( item.getDD() + dap.getDD()  );
			
		}
		System.out.println( JSONObject.toJSON( dap ) );
		
		
		System.out.println("basesh:" + (zjgj*10)* (1-dffy/(600D+dffy)) );
		
		Double gsjc = (dap.getGJSD()+( 0.8*30*dap.getHX() ))/100D +1;
		Double dcsh = (zjgj+dap.getGJL())*10 + (zjgj+dap.getGJL())*10*dap.getBJ()/100D + (zjgj+dap.getGJL())*10*dap.getBJ()/100D*dap.getBS()/100D + dap.getDD()*( 120*10 + (zjgj+dap.getGJL())*0.1*10)*0.3;
		Double js = 1-(dffy*(1-dap.getPJ()/100D))/(600D+ dffy*(1-dap.getPJ()/100D)) ;
		
		System.out.println("listsh:" + (gsjc)*(dcsh)* (js));
		
		
		System.out.println("basesm:" + zjsm );
		System.out.println("listsm:" + (zjsm +  dap.getSM()  +(dap.getXX()/100D) * (dap.getGJSD()/100D +1)*((zjgj+dap.getGJL())*10 + (zjgj+dap.getGJL())*10*dap.getBJ()/100D + (zjgj+dap.getGJL())*10*dap.getBJ()/100D*dap.getBS()/100D     )* (1-(dffy*(1-dap.getPJ()/100D))/(600D+ dffy*(1-dap.getPJ()/100D)) )));
		
		
		
		
		//无尽>宗师>红叉>电刀
		//破晓加成巨大60%多，但是很贵
		
	}

}
