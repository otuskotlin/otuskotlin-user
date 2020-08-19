import 'package:openapi_generator_annotations/openapi_generator_annotations.dart';

@Openapi(
    additionalProperties:
    AdditionalProperties(pubName: 'ok_user_api', pubAuthor: 'Sergey Okatov'),
    inputSpecFile: '../ok-user-openapi-spec-v1.yaml',
    generatorName: 'dart-jaguar',
    outputDirectory: 'api/ok_user_api')
class FlutterUser extends OpenapiGeneratorConfig {}
