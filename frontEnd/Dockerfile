FROM node:lts-alpine as angular
WORKDIR /app
COPY package.json /app
RUN npm install --silent
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=angular app/dist/front-end /usr/share/nginx/html
