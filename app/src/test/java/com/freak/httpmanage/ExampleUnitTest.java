package com.freak.httpmanage;

import com.freak.httpmanage.bean.UserEntity;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    UserEntity mUserEntity;


    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    /**
     * 这个类型的对象可能包含值，也可能为空。你可以使用同名方法创建一个空的 Optional。
     * 此方法会导致NoSuchElementException异常
     */
    @Test(expected = NoSuchElementException.class)
    public void whenCreateEmptyOptional_thenNull() {
        Optional<UserEntity> emptyOpt = Optional.empty();
        emptyOpt.get();
    }

    /**
     * 使用  of() 和 ofNullable() 方法创建包含值的 Optional。
     * 两个方法的不同之处在于如果你把 null 值作为参数传递进去，
     * of() 方法会抛出 NullPointerException
     * <p>
     * 我们并没有完全摆脱 NullPointerException。因此，你应该明确对象不为 null  的时候使用 of()
     * 如果对象即可能是 null 也可能是非 null，你就应该使用 ofNullable() 方法
     */
    @Test(expected = NullPointerException.class)
    public void whenCreate_OfEmptyOptional_thenNullPointerException() {
        Optional<UserEntity> opt = Optional.of(mUserEntity);
    }

    /**
     * 如果对象即可能是 null 也可能是非 null，你就应该使用 ofNullable() 方法
     */
    @Test
    public void whenCreate_null_OfNullableOptional_thenOk() {
        Optional<UserEntity> opt = Optional.ofNullable(null);
    }

    /**
     * 如果对象即可能是 null 也可能是非 null，你就应该使用 ofNullable() 方法
     * 从 Optional 实例中取回实际值对象的方法之一是使用 get() 方法
     * Optional.ofNullable(null)如果传入的值是null，则调用get() 方法会导致NoSuchElementException异常
     */
    @Test
    public void whenCreate_notNull_OfNullableOptional_thenOk() {
        String name = "John";
        Optional<String> opt = Optional.ofNullable(name);
        opt.get();
        assertEquals("John", opt.get());
    }

    /**
     * Optional.ofNullable(null)如果传入的值是null，则调用get() 方法会导致NoSuchElementException异常
     */
    @Test
    public void whenCreate_notNull_OfNullableOptional_thenNoSuchElementException() {
        Optional<String> opt = Optional.ofNullable(null);
        opt.get();
    }

    /**
     * Optional.ofNullable(null)如果传入的值是null，则调用get() 方法会导致NoSuchElementException异常
     * 要避免异常，你可以选择首先验证是否有值,调用isPresent() 方法检查是否有值
     */
    @Test
    public void whenCheck_IsPresent_thenOk() {
        UserEntity user = new UserEntity("freak", "1234");
        Optional<UserEntity> opt = Optional.ofNullable(user);
        assertTrue(opt.isPresent());
        assertEquals(user.getUserName(), opt.get().getUserName());
    }

    /**
     * 检查是否有值的另一个选择是 ifPresent() 方法。
     * 该方法除了执行检查，还接受一个Consumer(消费者) 参数
     * 如果对象不是空的，就对执行传入的 Lambda 表达式：
     * <p>
     * 这个例子中，只有 user 用户不为 null 的时候才会执行断言
     */
    @Test
    public void whenCheck_IfPresent_thenOk_Lambda() {
        UserEntity user = new UserEntity("freak", "1234");
        Optional<UserEntity> opt = Optional.ofNullable(user);
        opt.ifPresent(u -> assertEquals(user.getUserName(), u.getUserName()));
    }

    /**
     * 返回默认值
     * Optional 类提供了 API 用以返回对象值，或者在对象为空的时候返回默认值。
     * 可以使用的第一个方法是 orElse()，它的工作方式非常直接，如果有值则返回该值，否则返回传递给它的参数值
     * <p>
     * 这里 user 对象是空的，所以返回了作为默认值的 user2。
     * <p>
     * 如果对象的初始值不是 null，那么默认值会被忽略
     */
    @Test
    public void whenCreate_orElse_whenEmptyValue_thenReturnDefault() {
        UserEntity user = null;
        UserEntity user2 = new UserEntity("freak", "1234");
        UserEntity result = Optional.ofNullable(user).orElse(user2);
        assertEquals(user2.getUserName(), result.getUserName());
    }

    /**
     * 对象的初始值不是 null，那么默认值会被忽略
     */
    @Test
    public void whenCreate_orElse_whenValueNotNull_thenIgnoreDefault() {
        UserEntity user = new UserEntity("freak", "1234");
        UserEntity user2 = new UserEntity("freak1", "1234");
        UserEntity result = Optional.ofNullable(user).orElse(user2);
        assertEquals("freak", result.getUserName());
    }

    /**
     * 第二个同类型的 API 是 orElseGet() —— 其行为略有不同。
     * 这个方法会在有值的时候返回值，
     * 如果没有值，它会执行作为参数传入的 Supplier(供应者) 函数式接口，并将返回其执行结果
     */
    @Test
    public void whenCreate_orElseGet_themOk() {
//        UserEntity user = null;
        UserEntity user = new UserEntity("freak", "1234");
        UserEntity user2 = new UserEntity("freak1", "1234");
        UserEntity result = Optional.ofNullable(user).orElse(user2);
        assertEquals("freak", result.getUserName());
    }

    /**
     * orElse() 和 orElseGet() 的不同之处
     * <p>
     * 两种方法都调用了 createNewUser() 方法，这个方法会记录一个消息并返回 User 对象。
     * 当对象为空而返回默认对象时，行为并无差异
     * <p>
     * 当两个 Optional  对象都包含非空值，两个方法都会返回对应的非空值。不过，orElse() 方法仍然创建了 User 对象。
     * 与之相反，orElseGet() 方法不创建 User 对象。
     * <p>
     * 在执行较密集的调用时，比如调用 Web 服务或数据查询，这个差异会对性能产生重大影响。
     */
    @Test
    public void givenEmptyValue_whenCompare_thenOk() {
        UserEntity user = null;
        System.out.println("Using orElse");
        UserEntity result = Optional.ofNullable(user).orElse(createNewUser());
        System.out.println(result);
        System.out.println("Using orElseGet");
        UserEntity result2 = Optional.ofNullable(user).orElseGet(this::createNewUser);
        System.out.println(result2);
    }

    @Test
    public void givenPresentValue_whenCompare_thenOk() {
        UserEntity user = new UserEntity("freak", "1234");
        System.out.println("Using orElse");
        UserEntity result = Optional.ofNullable(user).orElse(createNewUser());
        System.out.println(result.toString());
        System.out.println("Using orElseGet");
        UserEntity result2 = Optional.ofNullable(user).orElseGet(this::createNewUser);
        System.out.println(result2.toString());
    }

    private UserEntity createNewUser() {
        System.out.println("Creating New User");
        return new UserEntity("freakCreate", "1234");
    }

    /**
     * 返回异常
     * <p>
     * 除了 orElse() 和 orElseGet() 方法，
     * Optional 还定义了 orElseThrow() API
     * 它会在对象为空的时候抛出异常，而不是返回备选的值
     * <p>
     * 这个方法让我们有更丰富的语义，可以决定抛出什么样的异常，而不总是抛出 NullPointerException。
     * <p>
     * 现在我们已经很好地理解了如何使用 Optional，我们来看看其它可以对 Optional 值进行转换和过滤的方法
     *
     * @throws Throwable
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenThrowException_thenOk() throws Throwable {
        UserEntity result = Optional.ofNullable(mUserEntity).orElseThrow(IllegalArgumentException::new);
        System.out.println(result.toString());
    }

    /**
     * 转换值
     * <p>
     * 有很多种方法可以转换 Optional  的值。我们从 map() 和 flatMap() 方法开始
     * <p>
     * 如果存在值，则返回将给定映射函数应用于值的Optional描述（如同 ofNullable(T)），否则返回空Optional。
     */
    @Test
    public void whenMap_thenOk() {
//        UserEntity user = new UserEntity("freak", "1234");
        UserEntity user = new UserEntity();
        String userName = Optional.ofNullable(user)
                .map(UserEntity::getUserName).orElse("freak1");
        System.out.println("userName->" + userName + "\nuser.getUserName()->" + user.getUserName());
//        assertEquals(userName, user.getUserName());
    }

    @Test
    public void whenFlatMap_thenOk() {
        UserEntity user = new UserEntity("freak", "1234");
//        UserEntity user = new UserEntity();
//        user.setPosition("Developer");
        String position = Optional.ofNullable(user)
                .flatMap(UserEntity::getPosition).orElse("default");
        System.out.println("position-》" + position);
//        assertEquals(position, user.getPosition().get());
    }

    /**
     * 过滤值
     * <p>
     * 除了转换值之外，Optional  类也提供了按条件“过滤”值的方法。
     * <p>
     * ilter() 接受一个 Predicate 参数，返回测试结果为 true 的值。
     * 如果测试结果为 false，会返回一个空的 Optional。
     * <p>
     * 如果通过过滤器测试，result 对象会包含非空值
     */
    @Test
    public void whenFilter_thenOk() {
        UserEntity user = new UserEntity("freak@email.com", "1234");
        Optional<UserEntity> result = Optional.ofNullable(user)
                .filter(u -> u.getUserName() != null && u.getUserName().contains("freak"));
        if (result.isPresent()) {
            System.out.println("result-》" + result.isPresent() + "\n" + result.get().getUserName() + "\n" + result.get().getPassword());
        } else {
            System.out.println("result-》" + result.isPresent());
        }
//        assertTrue(result.isPresent());
    }
}