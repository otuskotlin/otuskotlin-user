// This file is useless but avoids errors highlighting in Intellij Idea with Terraform Plugin

data "aws_caller_identity" "current" {}
data "aws_region" "current" {}
resource "aws_iam_role" "merged_0" {
  assume_role_policy = ""
}