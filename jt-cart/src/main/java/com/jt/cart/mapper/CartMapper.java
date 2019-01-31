package com.jt.cart.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.common.po.Cart;

public interface CartMapper extends SysMapper<Cart> {

	void updateCartNum(Cart cart);

	Cart findCartByUI(Cart cart);

}
