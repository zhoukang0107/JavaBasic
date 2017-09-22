package com.zack.basic.outofmemoryerror;

/**
 *
 */
public class FinalizeClient {
    public static FinalizeClient SAVE_HOOK;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        SAVE_HOOK = this;
    }

    public void isAlive(){
        System.out.println("i am still alive!");
    }


    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeClient();
        //
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("i am dead!");
        }

        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("i am dead!");
        }

    }
    static class Other{
        FinalizeClient finalizeClient;
    }
}
