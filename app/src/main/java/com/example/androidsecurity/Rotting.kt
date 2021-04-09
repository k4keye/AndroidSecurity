package com.example.androidsecurity

import android.util.Log
import java.io.File

/**
 * @version 1.0
 * @author K4keye
 *
 * 루팅 체크 관련 Class 파일이다.
 * 루팅은 안드로이드 사용자의 사용자 권한을
 * 최고 권한인 root권한으로 바꾸는행위이다.
 */
class Rotting {
    fun callProcess() : Boolean{
        val resultList = arrayOf(
            commandCheck(),
            tagCheck(),
            fileCheck())


        return resultList.contains(true)
    }

    /**
     * root 권한이 필요한 명령을 사용하는것으로
     * 루팅된 기기인지 체크한다.
     */
    private fun commandCheck()  : Boolean{
        val rootCommands = arrayOf("su")
        return try {
            for(rootCommand in rootCommands)
            {
                var data = Runtime.getRuntime().exec("su")
            }
            true
        }catch (e:Exception){
            false
        }
    }

    /**
     * 루팅된 기기는 Build.TAGS 값이 정상적인 제조사 키값이 아닌
     * test키값을 가지고있는 경우가있다.
     */
    private fun tagCheck() :Boolean{
        val denyTags = arrayOf("test-keys")

        val androidTag = android.os.Build.TAGS
        for(denyTag in denyTags){
            if(androidTag != null && androidTag.contains(denyTag)){
                return true
            }
        }

        return false
    }

    /**
     * 파일이름 기반 탐지로 루팅시 생성되는 파일의 존재를 검사한다.
     * // todo Magisk 앱도 차단 리스트에 넣어야한다.
     */
    private fun fileCheck()  : Boolean{
        val rootFiles = arrayOf(
            "/system/bin/su",
            "/system/sbin/su",
            "/system/xbin/su",
            "/system/bin/mu",
            "/system/bin/.ext/.su",
            "/system/usr/su-backup",
            "/system/app/Superuser.apk",
            "/system/app/Spapasu.apk",
            "/system/app/su.apk",
            "/system/bin/.ext",
            "/system/bin/.ext",
            "/data/data/com.noshufou.android.su", //superuser apk 의 AppCode
            "/data/local/bin/su",
            "/data/local/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su",
            "/sbin/su"
        )

        return try {
            for(rootfile in rootFiles)
            {
                var file:File=File(rootfile)
                if(null!=file && file.exists()){
                    true
                }
            }
            false
        }catch (e:Exception){
            false
        }
    }
}