from locust import HttpUser, task, between

class ApiGatewayDemo(HttpUser):
    wait_time = between(2,4)

    @task
    def community_page(self):

        headers = {
            "Authorization": "Bearer eyJraWQiOiJjOGQ4YTg0My00OWExLTRjMDMtYjEwOC1kMThlMGJhMGZjYTQiLCJ0eXAiOiJqd3QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJjNTJiZjdkYi1kYjU1LTRmODktYWM1My04MmI0MGU4YzU3YzIiLCJ3ZWJzaXRlIjoiaHR0cHM6Ly9leGFtcGxlLmNvbSIsInpvbmVpbmZvIjoiRXVyb3BlL0JlcmxpbiIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcm9maWxlIjoiaHR0cHM6Ly9leGFtcGxlLmNvbS9id2F5bmUiLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6OTAwMCIsInByZWZlcnJlZF91c2VybmFtZSI6ImJ3YXluZSIsImdpdmVuX25hbWUiOiJCcnVjZSIsImxvY2FsZSI6ImRlLURFIiwiYXVkIjoiZGVtby1jbGllbnQtcGtjZSIsIm5iZiI6MTY5ODA4NzExMSwidXBkYXRlZF9hdCI6IjE5NzAtMDEtMDFUMDA6MDA6MDBaIiwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsImVtYWlsIl0sIm5hbWUiOiJCcnVjZSBXYXluZSIsIm5pY2tuYW1lIjoiYndheW5lIiwiZXhwIjoxNjk4MDg4MDExLCJpYXQiOjE2OTgwODcxMTEsImZhbWlseV9uYW1lIjoiV2F5bmUiLCJlbWFpbCI6ImJydWNlLndheW5lQGV4YW1wbGUuY29tIn0.HH0sxNZhEoX1Mpl7-SQ23x355Pa-JmSS2tThPxOvVd_yhqd8Gemm4ZVNepMb3Y2W_SKeSVjhp24UlhRJPQGyqVgjghr3z3NVfKCmWfkCO1tQ0W1XAEC_4K3C8XPU3jkQoDGGvl7_Z5iAHPNq8HBD-82jVmI8uMxN61-FmHZCGvUy0md9STJkiIRov1etfadnP6_veDFz4CRoWKkqerrW_yzLUqIL1I-f-iRoXuQ8aZLIJH_Sa6DXvlTpJlwazE1Fcyoi9FJnlprZ9OVTWjOcdbHu3wuslwH8mSIDdUiP2_UWS4P4HahXU7HHvJRNY1isNevC4qnxuLeJY9IGfDLvTA"
        }

        self.client.get('/api/v1/community', headers=headers)