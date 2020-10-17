data "aws_iam_policy_document" "users_table" {
  statement {
    sid    = "AllowRWDynamoDBTable"
    effect = "Allow"

    actions = [
      "dynamodb:DescribeTable",
      "dynamodb:GetItem",
      "dynamodb:PutItem",
      "dynamodb:Scan",
      "dynamodb:Query",
      "dynamodb:UpdateItem",
      "dynamodb:DeleteItem",
    ]

    resources = [
      "arn:aws:dynamodb:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:table/users",
      "arn:aws:dynamodb:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:table/users/index/*",
    ]
  }
}

resource "aws_iam_role_policy" "crowdproj_teams_table" {
  role = "${aws_iam_role.app_kotless_0.name}"
  policy = "${data.aws_iam_policy_document.users_table.json}"
}

