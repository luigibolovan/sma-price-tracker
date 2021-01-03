from setuptools import setup, find_packages

setup(
    name='sma_price_tracker_server',
    version='1.0',
    long_description=__doc__,
    packages=find_packages(),
    include_package_data=True,
    zip_safe=False,
    install_requires=['Flask', 'bs4', 'nltk']
)
