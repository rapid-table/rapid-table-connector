package com.rapidtable.sdk.rtc4j.resource.project.schema.datetime;

/**
 * 日付範囲設定
 * @param refFieldId 参照先フィールドID
 * @param refFieldAs 参照先フィールドの自/至定義（from ... 参照先を開始日付 / to ... 参照先を終了日付）
 * @param limitLong 参照先フィールドの値からの選択幅
 * @param limitUnit 選択幅の単位
 */
public record DatetimeRangeSettings(String refFieldId, DatetimeRangeFieldAs refFieldAs, Integer limitLong, DatetimeSettingUnit limitUnit) {
}