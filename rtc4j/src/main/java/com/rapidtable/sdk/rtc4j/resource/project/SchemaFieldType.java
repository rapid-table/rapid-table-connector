/*
 * Copyright Rapid Table, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License. A copy of the License is located at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.rapidtable.sdk.rtc4j.resource.project;

public enum SchemaFieldType {
    /**
     * テキスト
     */
    Text,

    /**
     * 数値
     */
    Number,

    /**
     * 日時
     */
    DateTime,

    /**
     * チェックボックス
     */
    Check,

    /**
     * プルダウンボックス
     */
    Select,

    /**
     * ラジオボックス
     */
    Radio,

    /**
     * ラグラベル
     */
    Tag,

    /**
     * 電話/FAX
     */
    Phone,

    /**
     * E-Mail
     */

    EMail,

    /**
     * 郵便番号
     */
    ZipCode,

    /**
     * 添付ファイル(パス)
     */
    File,

    /**
     * 画像ファイル(パス)
     */
    Image,

    /**
     * 音声ファイル(パス)
     */
    Audio,

    /**
     * 動画ファイル(パス)
     */
    Video,

    /**
     * 評価
     */
    Rate,

    /**
     * URL
     */
    Url,

    /**
     * ユーザー参照
     */
    Users,

    /**
     * プロジェクト参照
     */
    Project,

    /**
     * 計算フィールド
     */
    Calculate;

}

