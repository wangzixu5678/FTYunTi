package com.example.ftkj.yunti_test.mvp;



import com.example.ftkj.yunti_test.mvp.contracts.MContract;
import com.example.ftkj.yunti_test.mvp.presenter.MBasePresenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by wwj on 2017/5/9.
 */

public class GenericHelper {

    public static <T> Class<T> getGenericClass(Class<?> klass, Class<?> filterClass) {
        Type type = klass.getGenericSuperclass();
        if(type == null || !(type instanceof ParameterizedType)) return null;
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        for (Type t : types) {
            if (isPresenter(t, filterClass)) {
                return (Class<T>) t;
            }
        }

        return null;
//        if(types == null || types.length == 0) return null;
//        return (Class<T>) types[0];
    }

    private static boolean isPresenter(Type t, Class<?> filterClass) {
        Class<?> aClass = (Class<?>) t;
        Class<?>[] classes = aClass.getInterfaces();
        for (Class<?> c : classes) {
            return c == filterClass || isPresenter(c, filterClass);
        }
        return false;
    }

    public static  <T> T newPresenter(Object obj) {
        if (!MContract.IView.class.isInstance(obj)) {
            throw new RuntimeException("no implement XContract.BaseView");
        }
        try {
            Class<?> currentClass = obj.getClass();
            Class<?> presenterClass = getGenericClass(currentClass, MContract.IPresenter.class);
            Class<?> modelClass = getGenericClass(presenterClass, MContract.IModel.class);
            MBasePresenter<?,?> mBasePresenter = (MBasePresenter<?, ?>) presenterClass.newInstance();
            mBasePresenter.init(obj, modelClass.newInstance());
            return (T) mBasePresenter;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("instance of presenter fail\n" +
                " Remind presenter need to extends XBasePresenter");
    }

}
