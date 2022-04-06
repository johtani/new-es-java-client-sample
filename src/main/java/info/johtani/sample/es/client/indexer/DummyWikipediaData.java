package info.johtani.sample.es.client.indexer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.johtani.sample.es.client.WikiDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DummyWikipediaData {

    private static String data0 = "{\"id\":\"5\",\"title\":\"アンパサンド\",\"timestamp\":\"2019-08-14T00:23:07Z\",\"revision_id\":\"1525028\",\"url\":\"https://ja.wikipedia.org/wiki?curid=5\",\"contents\":[\"アンパサンド (&、英語名：ampersand) とは並立助詞「…と…」を意味する記号である。ラテン語の \\\"et\\\" の合字で、Trebuchet MSフォントでは、と表示され \\\"et\\\" の合字であることが容易にわかる。ampersa、すなわち \\\"and per se and\\\"、その意味は\\\"and [the symbol which] by itself [is] and\\\"である。\"],\"categories\":[\"約物\",\"ラテン語の語句\",\"論理記号\"],\"headings\":[\"歴史\",\"手書き\",\"プログラミング言語\",\"符号位置\",\"外部リンク\"]}\n";
    private static String data1 = "{\"id\":\"12\",\"title\":\"地理学\",\"timestamp\":\"2020-05-19T20:22:44Z\",\"revision_id\":\"169753\",\"url\":\"https://ja.wikipedia.org/wiki?curid=12\",\"contents\":[\"地理学（ちりがく、、、伊：geografia、）は、。地域や空間、場所、自然環境という物理的存在を対象の中に含むことから、人文科学、社会科学、自然科学のいずれの性格も有する。広範な領域を網羅する。また「地理学と哲学は諸科学の母」と称される。\\n元来は農耕や戦争、統治のため、各地の情報を調査しまとめるための研究領域として成立した。\"],\"categories\":[\"地理学\",\"社会科学\",\"人文科学\",\"自然科学\",\"地球科学の分野\"],\"headings\":[\"地理学の歴史\",\"地理学の諸分野\",\"系統地理学\",\"自然地理学\",\"人文地理学\",\"地誌学\",\"研究方法\",\"地理学を学べる日本国内の大学\",\"教育上の問題点\",\"脚注\",\"注釈\",\"出典\",\"参考文献\",\"関連項目\",\"外部リンク\"]}\n";
    private static String data2 = "{\"id\":\"14\",\"title\":\"EU (曖昧さ回避)\",\"timestamp\":\"2018-05-12T23:38:14Z\",\"revision_id\":\"184560\",\"url\":\"https://ja.wikipedia.org/wiki?curid=14\",\"contents\":[\"EU\",\"EU\\n\\n  * 地理\\n    * 欧州連合 ()\\n    * ユローパ島 () の FIPS PUB 10-4 国名コード。\\n    * アメリカ合衆国 (; ) - ただし、スペイン語では複数形のため頭文字を重ねてEE.UU.もしくは正式国名Estados Unidos de Américaの略語のEUAで示されるのが普通で、EUは誤用とされ、通常使われない。\\n  * 作品タイトル\\n    * Europa Universalis - パラドックスインタラクティブ社の歴史シミュレーションゲーム。\\n    * 学警狙撃 () - 香港のテレビドラマ。\\n    * エントロピア・ユニバース () - スウェーデンのオンラインゲーム。\\n    * 拡張世界 () - スター・ウォーズのスピンオフ作品群。\\n  * 大学\\n    * エディンバラ大学 () - イギリス スコットランド エディンバラ。\\n    * 愛媛大学 () - 日本 愛媛県松山市。\\n    * エモリー大学 () - アメリカ ジョージア州アトランタ。\\n    * エロン大学 () - アメリカ カリフォルニア州エロン。\\n  * 実行ユニット ()\\n  * エクアトリアナ航空 (; IATA: EU) - エクアドルの航空会社 (1957–2005)。\\n  * 衝鋒隊 (香港) (, ) - 香港警察の機動隊。\\n  * ガリシア統一左翼 () - スペインの政党連合統一左翼 ()のガリシア州の支部政党。\"],\"categories\":[],\"headings\":[\"EU\",\"Eu\",\"eu\"]}\n";
    private static String data3 = "{\"id\":\"81\",\"title\":\"ゴーダチーズ\",\"timestamp\":\"2017-11-14T12:41:23Z\",\"revision_id\":\"981713\",\"url\":\"https://ja.wikipedia.org/wiki?curid=81\",\"contents\":[\"ゴーダチーズ（Gouda , 、 ）は、エダムチーズと並ぶオランダの代表的なチーズ。オランダでのチーズ生産量の60%を占める。ロッテルダム近郊の町、ゴーダで作られたことからこの名前がついた。正確な起源は不明だが12世紀頃にまで溯るとされることが多い。\\n外見は黄色がかった茶色い円盤型で、正式なサイズが直径35cm×高さ11cm・重さ約12kgと決められており、それより小さなものを総称して「ベビーゴーダ」と呼んでいる。中は白から黄色。熟成と共に色が変化する。熟成されたゴーダの中には表面が黒いものもある。\\nクミンシードやニンニクなどを用いて香りをつけたものもある。\\n主な材料は牛乳とレンネット（凝乳酵素）。\\nチーズの種類としてはセミハードに分類される。味はマイルドで日本では比較的広く親しまれている。\\nオランダでは土産物として空港などで売られている他、食料品店、チーズ販売店などでもほぼ置いている。チーズ店などでは特に包装をしていないものを常温で積み上げている場合もある（右図）。これは表面をロウでコーティングしてあり、ナイフを入れない限り熟成が急激に進む心配がないため。他に、フィルムにくるんだものや、真空パックのように包装したものもある。\\n日本では、チェダーチーズと並んでプロセスチーズの主要な原料として用いられているとされる。また、ゴーダチーズを原料としたスライスチーズが明治から販売されている。\",\"外部リンク\\n\\n  * 雪印メグミルク｜チーズクラブ「ゴーダ」\\n\"],\"categories\":[\"オランダのチーズ\",\"牛乳のチーズ\"],\"headings\":[\"外部リンク\"]}\n";

    public static List<WikiDocument> sampleDataList() throws IOException{
        List<WikiDocument> sampleDataList = new ArrayList<>();
        sampleDataList.add(toWikiDocument(data0));
        sampleDataList.add(toWikiDocument(data1));
        sampleDataList.add(toWikiDocument(data2));
        sampleDataList.add(toWikiDocument(data3));
        return sampleDataList;
    }

    private static WikiDocument toWikiDocument(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, WikiDocument.class);
    }

}
