// ISecurityCenter.aidl
package me.zhang.art.ipc.parcel;

interface ISecurityCenter {

    String encrypt(String content);

    String decrypt(String content);

}
