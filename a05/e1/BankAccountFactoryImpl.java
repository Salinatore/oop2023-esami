package a05.e1;

public class BankAccountFactoryImpl implements BankAccountFactory {

    @Override
    public BankAccount simple() {
        return new AbstractBankAccount() {

            @Override
            protected boolean canDeposit(int amount) {
                return true;
            }

            @Override
            protected void onDisallowedDeposit() {
            }

            @Override
            protected void onDisallowedWithdraw() {
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return this.balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                return this.balance() >= amount ? true : false;
            }

        };
    }

    @Override
    public BankAccount withFee(int fee) {
        return new AbstractBankAccount() {

            @Override
            protected boolean canDeposit(int amount) {
                return true;
            }

            @Override
            protected void onDisallowedDeposit() {
            }

            @Override
            protected void onDisallowedWithdraw() {
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance() - amount - fee;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                return this.balance() >= amount ? true : false;
            }
            
        };
    }

    @Override
    public BankAccount checked() {
        return new AbstractBankAccount() {

            @Override
            protected boolean canDeposit(int amount) {
                return amount >= 0 ? true : false;
            }

            @Override
            protected void onDisallowedDeposit() {
                throw new IllegalStateException("Must deposit positive amount");
            }

            @Override
            protected void onDisallowedWithdraw() {
                throw new IllegalStateException("Must withdraw positive amount");
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return this.balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                return this.balance() >= amount && amount > 0 ? true : false; 
            }
            
        };
    }

    @Override
    public BankAccount gettingBlocked() {
        return new AbstractBankAccount() {

            private boolean isActive = true;

            @Override
            protected boolean canDeposit(int amount) {
                return amount >= 0 && isActive ? true : false;
            }

            @Override
            protected void onDisallowedDeposit() {
                isActive = false;
            }

            @Override
            protected void onDisallowedWithdraw() {
                isActive = false;
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return this.balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                return this.balance() >= amount && amount > 0 && isActive ? true : false; 
            }
            
        };
    }

    @Override
    public BankAccount pool(BankAccount primary, BankAccount secondary) {
        return new BankAccount() {

            @Override
            public int balance() {
                return primary.balance() + secondary.balance();
            }

            @Override
            public void deposit(int amount) {
                (primary.balance() > secondary.balance() ? secondary : primary).deposit(amount);
            }

            @Override
            public boolean withdraw(int amount) {
                return primary.withdraw(amount) || secondary.withdraw(amount);
            }
            
        };
    }

}
