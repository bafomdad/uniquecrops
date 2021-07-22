package com.bafomdad.uniquecrops.data;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.JsonUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.stream.IntStream;

@JsonAdapter(CropGenerator.SerializerBlockState.class)
public class CropGenerator {

    public final String modelName;
    public final int ageSize;
    private static final String[] crops = new String[] {
            "crop_normal",
            "crop_artisia",
            "crop_precision",
            "crop_knowledge",
            "crop_dirigible",
            "crop_millennium",
            "crop_enderlily",
            "crop_collis",
            "crop_invisibilia",
            "crop_maryjane",
            "crop_weepingbells",
            "crop_musica",
            "crop_cinderbella",
            "crop_merlinia",
            "crop_eula",
            "crop_cobblonia",
            "crop_dyeius",
            "crop_abstract",
            "crop_wafflonia",
            "crop_devilsnare",
            "crop_pixelsius",
            "crop_petramia",
            "crop_malleatoris",
            "crop_imperia",
            "crop_lacusia",
            "crop_hexis",
            "crop_industria",
            "crop_quarry",
            "crop_donutsteel",
            "crop_instabilis",
            "crop_succo",
            "crop_adventus",
            "crop_holy",
            "crop_magnes",
            "crop_feroxia"
    };

    public CropGenerator(String modelName, int ageSize) {

        this.modelName = modelName;
        this.ageSize = ageSize;
    }

    public static void main(String[] args) {

//       for (int s = 0; s < crops.length; s++) {
//             String fileName = crops[s];
//            CropGenerator crop = new CropGenerator(fileName, 8);
//            File f = new File("src/generated/resources/blockstates/" + fileName + ".json");
//            JsonUtils.toJson(crop, new TypeToken<CropGenerator>() {}, f);
//        }
        String fileName = "crop_itero";
        CropGenerator crop = new CropGenerator(fileName, 8);
        File f = new File("src/generated/resources/blockstates/" + fileName + ".json");
        JsonUtils.toJson(crop, new TypeToken<CropGenerator>() {}, f);
    }

    public static class SerializerBlockState implements JsonSerializer<CropGenerator> {

        private int[] textureSkipper = IntStream.of(1, 2, 2, 3, 3, 4, 4, 5).toArray();

        @Override
        public JsonElement serialize(CropGenerator src, Type type, JsonSerializationContext ctx) {

            JsonObject obj = new JsonObject();
            JsonObject variant = new JsonObject();

            for (int i = 0; i < src.ageSize; i++) {
                String cropAge = "age=" + i;

                JsonObject model = new JsonObject();
                String modelName = UniqueCrops.MOD_ID + ":block/" + src.modelName.substring(5) + textureSkipper[i];
                model.addProperty("model", modelName);
                SerializerModel modelFile = new SerializerModel(modelName);
                File f = new File("src/generated/resources/models/block/" + src.modelName.substring(5) + textureSkipper[i] + ".json");
                JsonUtils.toJson(modelFile, new TypeToken<SerializerModel>() {}, f);

                variant.add(cropAge, model);
            }
            obj.add("variants", variant);
            return obj;
        }
    }

    public static class SerializerModel {

        private String parent;
        private JsonObject textures = new JsonObject();

        public SerializerModel(String textureName) {

            parent = "minecraft:block/crop";
            textures.addProperty("crop", textureName);
        }
    }
}
