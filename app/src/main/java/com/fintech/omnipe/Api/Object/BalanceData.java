package com.fintech.omnipe.Api.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceData implements Parcelable {

    @SerializedName("isPackageDeducionForRetailor")
    @Expose
    public boolean isPackageDeducionForRetailor;
    @SerializedName("balance")
    @Expose
    public Double balance;
    @SerializedName("isBalance")
    @Expose
    public boolean isBalance;
    @SerializedName("uBalance")
    @Expose
    public Double uBalance;
    @SerializedName("isUBalance")
    @Expose
    public boolean isUBalance;
    @SerializedName("bBalance")
    @Expose
    public Double bBalance;
    @SerializedName("isBBalance")
    @Expose
    public boolean isBBalance;
    @SerializedName("cBalance")
    @Expose
    public Double cBalance;
    @SerializedName("isCBalance")
    @Expose
    public boolean isCBalance;
    @SerializedName("idBalnace")
    @Expose
    public Double idBalnace;
    @SerializedName("isIDBalance")
    @Expose
    public boolean isIDBalance;
    @SerializedName("aepsBalnace")
    @Expose
    public Double aepsBalnace;
    @SerializedName("isAEPSBalance")
    @Expose
    public boolean isAEPSBalance;
    @SerializedName("packageBalnace")
    @Expose
    public Double packageBalnace;
    @SerializedName("isPacakgeBalance")
    @Expose
    public boolean isPacakgeBalance;
    @SerializedName("isP")
    @Expose
    public boolean isP;
    @SerializedName("isPN")
    @Expose
    public boolean isPN;
    @SerializedName("isAEPSBalanceFund")
    @Expose
    public boolean isAEPSBalanceFund;

    @SerializedName("isBalanceFund")
    @Expose
    public boolean isBalanceFund;
    @SerializedName("uCapping")
    @Expose
    public double uCapping;
    @SerializedName("isUBalanceFund")
    @Expose
    public boolean isUBalanceFund;
    @SerializedName("bbCapping")
    @Expose
    public double bbCapping;
    @SerializedName("isBBalanceFund")
    @Expose
    public boolean isBBalanceFund;
    @SerializedName("cCapping")
    @Expose
    public double cCapping;
    @SerializedName("isCBalanceFund")
    @Expose
    public boolean isCBalanceFund;
    @SerializedName("idCapping")
    @Expose
    public double idCapping;
    @SerializedName("isIDBalanceFund")
    @Expose
    public boolean isIDBalanceFund;
    @SerializedName("pacakgeBalance")
    @Expose
    public double pacakgeBalance;
    @SerializedName("packageCapping")
    @Expose
    public double packageCapping;
    @SerializedName("isPacakgeBalanceFund")
    @Expose
    public boolean isPacakgeBalanceFund;
    @SerializedName("isLowBalance")
    @Expose
    public boolean isLowBalance;
    @SerializedName("commRate")
    @Expose
    public double commRate;
    @SerializedName("isQRMappedToUser")
    @Expose
    public boolean isQRMappedToUser;
    @SerializedName("osBalance")
    @Expose
    public double osBalance;

    @SerializedName("prepaidWalletName")
    @Expose
    private String prepaidWalletName;
    @SerializedName("utilityWalletName")
    @Expose
    private String utilityWalletName;
    @SerializedName("bankWalletName")
    @Expose
    private String bankWalletName;
    @SerializedName("cardWalletName")
    @Expose
    private String cardWalletName;
    @SerializedName("regIDWalletName")
    @Expose
    private String regIDWalletName;
    @SerializedName("packageWalletName")
    @Expose
    private String packageWalletName;

    protected BalanceData(Parcel in) {
        isPackageDeducionForRetailor = in.readByte() != 0;
        if (in.readByte() == 0) {
            balance = null;
        } else {
            balance = in.readDouble();
        }
        isBalance = in.readByte() != 0;
        if (in.readByte() == 0) {
            uBalance = null;
        } else {
            uBalance = in.readDouble();
        }
        isUBalance = in.readByte() != 0;
        if (in.readByte() == 0) {
            bBalance = null;
        } else {
            bBalance = in.readDouble();
        }
        isBBalance = in.readByte() != 0;
        if (in.readByte() == 0) {
            cBalance = null;
        } else {
            cBalance = in.readDouble();
        }
        isCBalance = in.readByte() != 0;
        if (in.readByte() == 0) {
            idBalnace = null;
        } else {
            idBalnace = in.readDouble();
        }
        isIDBalance = in.readByte() != 0;
        if (in.readByte() == 0) {
            aepsBalnace = null;
        } else {
            aepsBalnace = in.readDouble();
        }
        isAEPSBalance = in.readByte() != 0;
        if (in.readByte() == 0) {
            packageBalnace = null;
        } else {
            packageBalnace = in.readDouble();
        }
        isPacakgeBalance = in.readByte() != 0;
        isP = in.readByte() != 0;
        isPN = in.readByte() != 0;
        isAEPSBalanceFund = in.readByte() != 0;
        isBalanceFund = in.readByte() != 0;
        uCapping = in.readDouble();
        isUBalanceFund = in.readByte() != 0;
        bbCapping = in.readDouble();
        isBBalanceFund = in.readByte() != 0;
        cCapping = in.readDouble();
        isCBalanceFund = in.readByte() != 0;
        idCapping = in.readDouble();
        isIDBalanceFund = in.readByte() != 0;
        pacakgeBalance = in.readDouble();
        packageCapping = in.readDouble();
        isPacakgeBalanceFund = in.readByte() != 0;
        isLowBalance = in.readByte() != 0;
        commRate = in.readDouble();
        isQRMappedToUser = in.readByte() != 0;
        osBalance = in.readDouble();
        prepaidWalletName = in.readString();
        utilityWalletName = in.readString();
        bankWalletName = in.readString();
        cardWalletName = in.readString();
        regIDWalletName = in.readString();
        packageWalletName = in.readString();
    }

    public static final Creator<BalanceData> CREATOR = new Creator<BalanceData>() {
        @Override
        public BalanceData createFromParcel(Parcel in) {
            return new BalanceData(in);
        }

        @Override
        public BalanceData[] newArray(int size) {
            return new BalanceData[size];
        }
    };

    public double getOsBalance() {
        return osBalance;
    }

    public boolean isQRMappedToUser() {
        return isQRMappedToUser;
    }

    public boolean isPackageDeducionForRetailor() {
        return isPackageDeducionForRetailor;
    }

    public Double getBalance() {
        return balance;
    }

    public boolean isBalance() {
        return isBalance;
    }

    public Double getuBalance() {
        return uBalance;
    }

    public boolean isUBalance() {
        return isUBalance;
    }

    public Double getbBalance() {
        return bBalance;
    }

    public boolean isBBalance() {
        return isBBalance;
    }

    public Double getcBalance() {
        return cBalance;
    }

    public boolean isCBalance() {
        return isCBalance;
    }

    public Double getIdBalnace() {
        return idBalnace;
    }

    public boolean isIDBalance() {
        return isIDBalance;
    }

    public Double getAepsBalnace() {
        return aepsBalnace;
    }

    public boolean isAEPSBalance() {
        return isAEPSBalance;
    }

    public Double getPackageBalnace() {
        return packageBalnace;
    }

    public boolean isPacakgeBalance() {
        return isPacakgeBalance;
    }

    public boolean isP() {
        return isP;
    }

    public boolean isPN() {
        return isPN;
    }

    public boolean isAEPSBalanceFund() {
        return isAEPSBalanceFund;
    }

    public boolean isBalanceFund() {
        return isBalanceFund;
    }

    public double getuCapping() {
        return uCapping;
    }

    public boolean isUBalanceFund() {
        return isUBalanceFund;
    }

    public double getBbCapping() {
        return bbCapping;
    }

    public boolean isBBalanceFund() {
        return isBBalanceFund;
    }

    public double getcCapping() {
        return cCapping;
    }

    public boolean isCBalanceFund() {
        return isCBalanceFund;
    }

    public double getIdCapping() {
        return idCapping;
    }

    public boolean isIDBalanceFund() {
        return isIDBalanceFund;
    }

    public double getPacakgeBalance() {
        return pacakgeBalance;
    }

    public double getPackageCapping() {
        return packageCapping;
    }

    public boolean isPacakgeBalanceFund() {
        return isPacakgeBalanceFund;
    }

    public boolean isLowBalance() {
        return isLowBalance;
    }

    public double getCommRate() {
        return commRate;
    }


    public String getPrepaidWalletName() {
        return prepaidWalletName;
    }

    public String getUtilityWalletName() {
        return utilityWalletName;
    }

    public String getBankWalletName() {
        return bankWalletName;
    }

    public String getCardWalletName() {
        return cardWalletName;
    }

    public String getRegIDWalletName() {
        return regIDWalletName;
    }

    public String getPackageWalletName() {
        return packageWalletName;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (isPackageDeducionForRetailor ? 1 : 0));
        if (balance == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(balance);
        }
        parcel.writeByte((byte) (isBalance ? 1 : 0));
        if (uBalance == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(uBalance);
        }
        parcel.writeByte((byte) (isUBalance ? 1 : 0));
        if (bBalance == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(bBalance);
        }
        parcel.writeByte((byte) (isBBalance ? 1 : 0));
        if (cBalance == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(cBalance);
        }
        parcel.writeByte((byte) (isCBalance ? 1 : 0));
        if (idBalnace == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(idBalnace);
        }
        parcel.writeByte((byte) (isIDBalance ? 1 : 0));
        if (aepsBalnace == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(aepsBalnace);
        }
        parcel.writeByte((byte) (isAEPSBalance ? 1 : 0));
        if (packageBalnace == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(packageBalnace);
        }
        parcel.writeByte((byte) (isPacakgeBalance ? 1 : 0));
        parcel.writeByte((byte) (isP ? 1 : 0));
        parcel.writeByte((byte) (isPN ? 1 : 0));
        parcel.writeByte((byte) (isAEPSBalanceFund ? 1 : 0));
        parcel.writeByte((byte) (isBalanceFund ? 1 : 0));
        parcel.writeDouble(uCapping);
        parcel.writeByte((byte) (isUBalanceFund ? 1 : 0));
        parcel.writeDouble(bbCapping);
        parcel.writeByte((byte) (isBBalanceFund ? 1 : 0));
        parcel.writeDouble(cCapping);
        parcel.writeByte((byte) (isCBalanceFund ? 1 : 0));
        parcel.writeDouble(idCapping);
        parcel.writeByte((byte) (isIDBalanceFund ? 1 : 0));
        parcel.writeDouble(pacakgeBalance);
        parcel.writeDouble(packageCapping);
        parcel.writeByte((byte) (isPacakgeBalanceFund ? 1 : 0));
        parcel.writeByte((byte) (isLowBalance ? 1 : 0));
        parcel.writeDouble(commRate);
        parcel.writeByte((byte) (isQRMappedToUser ? 1 : 0));
        parcel.writeDouble(osBalance);
        parcel.writeString(prepaidWalletName);
        parcel.writeString(utilityWalletName);
        parcel.writeString(bankWalletName);
        parcel.writeString(cardWalletName);
        parcel.writeString(regIDWalletName);
        parcel.writeString(packageWalletName);
    }
}
