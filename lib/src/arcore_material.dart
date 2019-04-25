import 'dart:ui';

import 'package:meta/meta.dart';

class ArCoreMaterial {
  final MaterialFactory materialFactory;
  final Color color;
  final String texture;

  ///  The metallic property defines whether the surface is a metallic (conductor)
  /// or a non-metallic (dielectric) surface. This property should be used as a
  /// binary value, set to either 0 or 1. Intermediate values are only truly useful
  /// to create transitions between different types of surfaces when using textures.
  /// The default value is 0.
  final double metallic; //0 or 1 , for texture between 0 and 1

  /// The roughness property controls the perceived smoothness of the surface.
  /// When roughness is set to 0, the surface is perfectly smooth and highly glossy.
  /// The rougher a surface is, the âblurrierâ the reflections are.
  /// The default value is 0.4.
  final double roughness;

  /// The reflectance property only affects non-metallic surfaces.
  /// This property can be used to control the specular intensity. This value
  /// is defined between 0 and 1 and represents a remapping of a percentage of
  /// reflectance. The default value is 0.5.
  final double reflectance; //between 0 and 1

  ArCoreMaterial({this.metallic,
    this.roughness,
    this.reflectance,
    this.color,
    this.texture,
    this.materialFactory});

  Map<String, dynamic> toMap() =>
      <String, dynamic>{
        'color': [color.alpha, color.red, color.green, color.blue],
        'texture': this.texture,
        'metallic': this.metallic,
        'roughness': this.roughness,
        'reflectance': this.reflectance,
        'materialType': materialFactory.index
      }
        ..removeWhere((String k, dynamic v) => v == null);
}

enum MaterialFactory {
  OPAQUE_WITH_COLOR,
  TRANSPARENT_WITH_COLOR,
  OPAQUE_WITH_TEXTURE,
  TRANSPARENT_WITH_TEXTURE
}
