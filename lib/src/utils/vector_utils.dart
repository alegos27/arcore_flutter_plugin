import 'package:vector_math/vector_math_64.dart';

Vector3 vector3fromArray(List<double> floatArray) {
  return Vector3(floatArray[0], floatArray[1], floatArray[2]);
}

Map<String, double>? convertVector3ToMap(Vector3? vector) =>
    vector == null ? null : {'x': vector.x, 'y': vector.y, 'z': vector.z};

Map<String, double>? convertVector4ToMap(Vector4? vector) => vector == null
    ? null
    : {'x': vector.x, 'y': vector.y, 'z': vector.z, 'w': vector.w};
