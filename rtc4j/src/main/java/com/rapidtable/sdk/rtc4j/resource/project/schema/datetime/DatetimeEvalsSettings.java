package com.rapidtable.sdk.rtc4j.resource.project.schema.datetime;

/**
 * 日付計算設定
 * @param originFieldId 参照先Datetime型フィールドID
 * @param numberFieldId 参照先Number/Calc型フィールドID
 * @param numberUnit 選択幅の単位
 */
public record DatetimeEvalsSettings(String originFieldId, String numberFieldId, DatetimeSettingUnit numberUnit) {
}
