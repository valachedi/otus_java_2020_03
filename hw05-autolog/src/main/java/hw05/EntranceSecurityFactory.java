package hw05;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

class EntranceSecurityFactory {

  private EntranceSecurityFactory() {
  }

  static EntranceSecurityInterface createInstance() {
    return (EntranceSecurityInterface) Proxy.newProxyInstance(
                                          EntranceSecurityFactory.class.getClassLoader(),
                                          new Class<?>[]{EntranceSecurityInterface.class},
                                          new MethodInvocationHandler(new EntranceSecurity())
                                        );
  }

  static class MethodInvocationHandler implements InvocationHandler {
    private final EntranceSecurityInterface myClass;
    private static ArrayList<String> methodNamesLoggable;

    MethodInvocationHandler(EntranceSecurityInterface myClass) {
      this.myClass = myClass;

      if(methodNamesLoggable == null) {
        cacheLoggableMethodNames();
      }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if(methodNamesLoggable.contains(method.getName())) {
        System.out.println("executed method: " + method.getName() + ", param: " + args[0]);
      }

      return method.invoke(myClass, args);
    }

    private void cacheLoggableMethodNames() {
      methodNamesLoggable = new ArrayList<String>(1);

      for(Method eachMethod : EntranceSecurityInterface.class.getMethods()) {
        if(eachMethod.isAnnotationPresent(Log.class)) {
          methodNamesLoggable.add(eachMethod.getName());
        }
      }
    }
  }
}
