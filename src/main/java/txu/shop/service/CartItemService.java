package txu.shop.service;

import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import txu.common.exception.BadParameterException;
import txu.common.exception.NotFoundException;
import txu.shop.dao.CartItemDao;
import txu.shop.dao.ProductDao;
import txu.shop.dao.UserDao;
import txu.shop.dto.CartItemDto;
import txu.shop.entity.CartItemEntity;
import txu.shop.entity.ProductEntity;
import txu.shop.entity.UserEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final UserDao userDao;
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    @Transactional
    public CartItemEntity getByUerIdProductId(String userId, String productId){

        CartItemEntity cartItem = cartItemDao.getByUerIdProductId(userId, productId);
        if (cartItem == null){
            throw new NotFoundException("CartItem is not found");
        }
        return cartItem;
    }

    @Transactional
    public List<CartItemEntity> getByUerId(String userId){

        List<CartItemEntity> cartItem = cartItemDao.getByUerId(userId);
        if (cartItem == null){
            throw new NotFoundException("CartItem is not found");
        }
        return cartItem;
    }

    @Transactional
    public int deleteByUserId(String userId){
        return cartItemDao.deleteByUserId(userId);
    }

    public CartItemEntity create(CartItemDto cartItemDto){

        if (cartItemDto.getUsername() == null){
            throw new BadParameterException("username field is required");
        }

        if ( cartItemDto.getProductId() == null){
            throw new BadParameterException("productId field is required");
        }

        UserEntity user = userDao.getByUsername(cartItemDto.getUsername());
        if (user == null){
            throw  new NotFoundException(String.format("User '%s' is not found", cartItemDto.getUsername()));
        }

        ProductEntity product = productDao.findById(cartItemDto.getProductId());
        if (product == null){
            throw new NotFoundException(String.format("Product '%s' is not found", cartItemDto.getProductId()));
        }

        CartItemEntity cartItem = new CartItemEntity();

        cartItem.setProductEntity(product);
        cartItem.setUserEntity(user);
        cartItem.setQuantity(1);
        cartItem.setCreateDatetime(DateTime.now().toDate());
        cartItem.setUpdateDatetime(DateTime.now().toDate());

        return cartItemDao.save(cartItem);
    }

    public CartItemEntity quantityPlus(String cartId){
        var cart = cartItemDao.findById(cartId);
        if (cart == null){
            throw new NotFoundException(String.format("Cart with id '%s'  not found", cartId));
        }

        cart.setQuantity(1 + cart.getQuantity());
        return cartItemDao.save(cart);
    }

    public CartItemEntity quantityMinus(String cartId){
        var cart = cartItemDao.findById(cartId);
        if (cart == null){
            throw new NotFoundException(String.format("Cart with id '%s'  not found", cartId));
        }

        var quantity = cart.getQuantity();
        if (quantity >= 2){
            quantity = quantity -1;
        }

        cart.setQuantity( quantity);
        return cartItemDao.save(cart);
    }
}
