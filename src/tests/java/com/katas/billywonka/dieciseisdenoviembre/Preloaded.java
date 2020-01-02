package com.katas.billywonka.dieciseisdenoviembre;

import java.util.List;

public class Preloaded {
	public static boolean verify(int[] is, List<Integer> list) {
		List<Integer> respuesta=(new FPT(is)).solve();
		boolean check=(respuesta!=null)?(respuesta.size()==list.size()):(list==null);
		if((check)&&(respuesta!=null)){
			for(Integer i:respuesta)
				if((i<0)||(i>99))
					return false;
		}
		return check;			
	}
}
